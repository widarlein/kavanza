package com.github.widarlein.kavanza

import com.eatthepath.otp.TimeBasedOneTimePasswordGenerator
import com.github.widarlein.kavanza.model.*
import com.github.widarlein.kavanza.model.getordersanddeals.GetDealsResponse
import com.github.widarlein.kavanza.model.getordersanddeals.GetOrdersResponse
import com.github.widarlein.kavanza.model.order.*
import com.github.widarlein.kavanza.model.positions.Positions
import com.github.widarlein.kavanza.service.AvanzaService
import com.github.widarlein.kavanza.service.HeaderInterceptor
import com.github.widarlein.kavanza.service.LoggingInterceptor
import com.google.common.io.BaseEncoding
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.time.Instant
import java.util.*
import javax.crypto.spec.SecretKeySpec

private const val BASE_URL = "https://www.avanza.se/"
private const val TRANSACTION_COOKIE = "AZAMFATRANSACTION="

class AvanzaClient(private val debugPrintouts: Boolean = false) : IAvanzaClient {

    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .client(
            OkHttpClient.Builder()
                .addInterceptor(HeaderInterceptor).also {
                    if (debugPrintouts) {
                        it.addInterceptor(LoggingInterceptor)
                    }
                }
                .build()
        )
        .build()

    init {

    }

    private val avanzaService: AvanzaService = retrofit.create(AvanzaService::class.java)

    internal fun login(userCredentials: UserCredentials, totpSecret: String) {
        val loginCall: Call<LoginResponse> = avanzaService.login(userCredentials)
        val loginCallResponse = loginCall.execute()
        if (!loginCallResponse.isSuccessful) {
            throw RuntimeException("Could not perform login procedure response was ${loginCallResponse.message()} body: ${loginCallResponse.errorBody()?.string()}")
        }
        val loginResponse = loginCallResponse.body()

        check(loginResponse != null && loginResponse.twoFactorLogin.method == "TOTP") { "2FA method ${loginResponse?.twoFactorLogin?.method} is not supported" }

        val baseEncoding = BaseEncoding.base32()
        val totp = TimeBasedOneTimePasswordGenerator()
        val secretKeySpec = SecretKeySpec(baseEncoding.decode(totpSecret), "SHA1")

        val now = Instant.now()
        val totpCode = totp.generateOneTimePassword(secretKeySpec, now)

        val totpCall: Call<TotpResponse> = avanzaService.totp(
            TRANSACTION_COOKIE + loginResponse.twoFactorLogin.transactionId,
            TotpRequest(totpCode = totpCode.toTotpString())
        )
        val totpCallResponse: Response<TotpResponse> = totpCall.execute()

        val totpResponse = totpCallResponse.body()
        if (totpCallResponse.isSuccessful && totpResponse != null) {
            val cookies = totpCallResponse.headers().values("Set-Cookie")
            val csid = cookies.firstOrNull { it.startsWith("CSID=", ignoreCase = true) }?.substringAfter('=')?.substringBefore(';')
            val cstoken = cookies.firstOrNull { it.startsWith("CSTOKEN=", ignoreCase = true) }?.substringAfter('=')?.substringBefore(';')
            checkNotNull(csid) { "CSID cookie not found in response headers" }
            checkNotNull(cstoken) { "CSTOKEN cookie not found in response headers" }
            HeaderInterceptor.authenticationHeaders = HeaderInterceptor.AuthenticationHeaders(
                securityToken = totpCallResponse.headers()["X-SecurityToken"]!!,
                csid = csid,
                cstoken = cstoken
            )
        } else {
            throw RuntimeException("Could not perform login totp procedure response was ${totpCallResponse.message()} body: ${totpCallResponse.errorBody()?.string()}")
        }

    }

    /**
     * Get overview of the logged in user account
     */
    override fun getOverview(): Overview {
        val response = avanzaService.getOverview().execute()
        check(response.isSuccessful) {"Overview request not successful ${response.message()} body: ${response.errorBody()?.string()}"}
        return response.body()!!
    }


    override fun getCategorizedAccountsOverview(): CategorizedAccountsOverview {
        val response = avanzaService.getCategorizedAccountsOverview().execute()
        check(response.isSuccessful) {"Categorized accounts overview request not successful ${response.message()} body: ${response.errorBody()?.string()}"}
        return response.body()!!
    }

    /**
     * Get the positions of the logged in user
     */
    override fun getPositions(): Positions {
        val response = avanzaService.getPostions().execute()
        check(response.isSuccessful) { "Positions request not successful ${response.message()} body: ${response.errorBody()?.string()}" }
        return response.body()!!
    }

    /**
     * Gets all transactions of a specified account
     *
     * @param accountId the id of the account in question
     * @param transactionOptions options regarding the wanted transactions
     * @return the transactions
     */
    override fun getTransactions(accountId: AccountId, transactionOptions: TransactionOptions?) = getTransactions(accountId.value, transactionOptions)

    /**
     * Gets all transactions of a specified type
     *
     * @param transactionType the type of the wanted transactions
     * @param transactionOptions options regarding the wanted transactions
     * @return the transactions
     */
    override fun getTransactions(transactionType: TransactionType, transactionOptions: TransactionOptions?) = getTransactions(transactionType.transactionType, transactionOptions)


    private fun getTransactions(accountOrTransactionType: String, transactionOptions: TransactionOptions? = null): Transactions {
        val queryMap = if (transactionOptions != null) {
            transactionOptions.toQueryMap()
        } else {
            mapOf()
        }

        val response = avanzaService.getTransactions(accountOrTransactionType, queryMap).execute()
        check(response.isSuccessful) {"Transactions request not successful ${response.message()} body: ${response.errorBody()?.string()}"}
        return response.body()!!
    }

    /**
     * Gets all watchlists
     *
     * @return the watchlists
     */
    override fun getWatchlists(): List<Watchlist> {
        val response = avanzaService.getWatchlists().execute()
        check(response.isSuccessful) {"Watchlist request not successful ${response.message()} body: ${response.errorBody()?.string()}"}
        return response.body()!!
    }

    /**
     * Adds a specific orderbook (equity security: stock, certificate, etc) to a given watchlist
     *
     * @param watchlistId the ID of the watchlist in question
     * @param orderbookId the ID of the security
     */
    override fun addToWatchlist(watchlistId: String, orderbookId: String) {
        val response = avanzaService.addToWatchlist(watchlistId, orderbookId).execute()
        check(response.isSuccessful) {"Add to watchlist request not successful ${response.message()} body: ${response.errorBody()?.string()}"}
    }

    /**
     * Removes a specific orderbook (equity security: stock, certificate, etc) from a given watchlist
     *
     * @param watchlistId the ID of the watchlist in question
     * @param orderbookId the ID of the security
     */
    override fun removeFromWatchlist(watchlistId: String, orderbookId: String) {
        val response = avanzaService.removeFromWatchlist(watchlistId, orderbookId).execute()
        check(response.isSuccessful) {"Remove from watchlist request not successful ${response.message()} body: ${response.errorBody()?.string()}"}
    }

    /**
     * Get details about one or more orderbooks
     *
     * @param orderbookIds a list of orderbook IDs
     * @return a list of orderbook overviews
     */
    override fun getOrderbooks(orderbookIds: List<String>): List<OrderbookListItem> {
        val response = avanzaService.getOrderbooks(orderbookIds.joinToString(",")).execute()
        check(response.isSuccessful) {"Orderbook list request not successful ${response.message()} body: ${response.errorBody()?.string()}"}
        return response.body()!!
    }

    override fun getMarketGuideStock(orderbookId: String): MarketGuideStock {
        val response = avanzaService.getMarketGuideStock(orderbookId).execute()
        check(response.isSuccessful) {"Market guide stock request not successful ${response.message()} body: ${response.errorBody()?.string()}"}
        return response.body()!!
    }

    /**
     * Gets prices over time for a specific orderbook
     *
     * @param orderbookId the ID of the orderbook in question
     * @param period the period over which to get the price
     * @param resolution the resolution of the price data
     * @return chart data {@link com.github.widarlein.kavanza.model.PriceChart}
     */
    override fun getStockPriceChart(orderbookId: String, period: TimePeriod, resolution: ResolutionType): PriceChart {
        val response = avanzaService.getStockPriceChart(orderbookId, period, resolution).execute()
        check(response.isSuccessful) {"Stock price chart request not successful ${response.message()} body: ${response.errorBody()?.string()}"}
        return response.body()!!
    }

    /**
     * Gets the exchange rate between two currencies
     *
     * @param fromCurrency the currency to convert from
     * @param toCurrency the currency to convert to
     * @return the exchange rate
     */
    override fun getExchangeRate(fromCurrency: String, toCurrency: String): ExchangeRate {
        val response = avanzaService.getExchangeRate(fromCurrency, toCurrency).execute()
        check(response.isSuccessful) {"Exchange rate request not successful ${response.message()} body: ${response.errorBody()?.string()}"}
        return response.body()!!
    }

    /**
     * Gets details about a specified instrument
     *
     * @param instrumentType the type of instrument wanted
     * @param instrumentId the id of the instrument in question
     * @return instrument data
     */
    @Deprecated("Endpoint changed in the app and this has not yet been migrated. Do not use")
    override fun getInstrument(instrumentType: InstrumentType, instrumentId: String): InstrumentData {
        throw NotImplementedError()
    }

    @Deprecated("Needs to be properly fixed with a custom deserializer")
    override fun getInspirationLists(): List<InspirationList> {
        TODO("Endpoint not returning reliable data. See FIXME in related AvanzaService.kt endpoint")
        val response = avanzaService.getInspirationLists().execute()
        check(response.isSuccessful) {"Inspiration lists request not successful ${response.message()} body: ${response.errorBody()?.string()}"}
        return response.body()!!
    }

    @Deprecated("Needs to be properly fixed with a custom deserializer")
    override fun getInspirationLists(listType: ListType): InspirationList {
        TODO("Endpoint not returning reliable data. See FIXME in related AvanzaService.kt endpoint")
        val response = avanzaService.getInspirationList(listType).execute()
        check(response.isSuccessful) {"Inspiration list request not successful ${response.message()} body: ${response.errorBody()?.string()}"}
        return response.body()!!
    }

    /**
     * Place an order
     *
     * @param orderOptions options about the order to place
     * @return a response indicating status and id of the order operation
     */
    override fun placeOrder(orderOptions: OrderOptions): OrderOperationResponse {
        val response = avanzaService.placeOrder(orderOptions).execute()
        check(response.isSuccessful) {"Place order request not successful ${response.message()} body: ${response.errorBody()?.string()}"}
        return response.body()!!
    }

    /**
     * Gets all orders of the logged in user
     *
     * @return a response containing the list of orders
     */
    override fun getOrders(): GetOrdersResponse {
        val response = avanzaService.getOrders().execute()
        check(response.isSuccessful) {"Get orders request not successful ${response.message()} body: ${response.errorBody()?.string()}"}
        return response.body()!!
    }

    /**
     * Gets all deals of the logged in user
     *
     * @return a response containing the list of deals
     */
    override fun getDeals(): GetDealsResponse {
        val response = avanzaService.getDeals().execute()
        check(response.isSuccessful) {"Get deals request not successful ${response.message()} body: ${response.errorBody()?.string()}"}
        return response.body()!!
    }

    /**
     * Get a specific order
     *
     * @param instrumentType the type of the security in the order
     * @param accountId the ID of the account of the order
     * @param orderId the ID of the order
     * @return the order
     */
    @Deprecated("Doesn't work, don't know the replacement")
    override fun getOrder(instrumentType: InstrumentType, accountId: AccountId, orderId: String): Order {
        val response = avanzaService.getOrder(instrumentType, accountId.value, orderId).execute()
        check(response.isSuccessful) {"Edit order request not successful ${response.message()} body: ${response.errorBody()?.string()}"}
        return response.body()!!
    }

    /**
     * Modify an order
     *
     * @param orderOptions options about the order to place
     * @return a response indicating status and id of the order operation
     */
    override fun modifyOrder(orderOptions: OrderOptions): OrderOperationResponse {
        val response = avanzaService.modifyOrder(orderOptions).execute()
        check(response.isSuccessful) {"Edit order request not successful ${response.message()} body: ${response.errorBody()?.string()}"}
        return response.body()!!
    }

    /**
     * Delete an order
     *
     * @param accountId the ID of the account of the order
     * @param orderId the ID of the order
     * @return a response indicating status and id of the order operation
     */
    override fun deleteOrder(accountId: AccountId, orderId: String): OrderOperationResponse {
        val response = avanzaService.deleteOrder(
            DeleteOrderOperation(
                accountId = accountId,
                orderId = orderId,
            )
        ).execute()
        check(response.isSuccessful) {"Delete order request not successful ${response.message()} body: ${response.errorBody()?.string()}"}
        return response.body()!!
    }

    /**
     * Place a stop loss order
     * @param orderOperation options about the stop loss order to place
     * @return a response indicating status and id of the order operation
     */
    override fun placeStopLoss(orderOperation: StopLossOperation): StopLossOperationResponse {
        val response = avanzaService.placeStopLossOrder(orderOperation).execute()
        check(response.isSuccessful) {"Place stop loss order request not successful ${response.message()} body: ${response.errorBody()?.string()}"}
        return response.body()!!
    }

    /**
     * Delete a stop loss order
     * @param accountId the ID of the account of the order
     * @param stopLossId the ID of the stop loss order
     */
    override fun deleteStopLoss(accountId: AccountId, stopLossId: String) {
        val response = avanzaService.deleteStopLoss(accountId.value, stopLossId).execute()
        check(response.isSuccessful) {"Delete stop loss request not successful ${response.message()} body: ${response.errorBody()?.string()}"}
    }

    /**
     * Free text search for an instrument
     *
     * @param searchQuery the text query to search for
     * @param instrumentType only search for instruments of this type. Defaults to null which searches among all types
     * @return the result of the search query
     */
    override fun search(searchQuery: String, instrumentType: InstrumentType?): SearchResponse {
        val searchRequest = SearchRequest(
            query = searchQuery,
            originPath = "/start",
            originPlatform = "PWA",
            pagination = Pagination(0, 30),
            screenSize = "DESKTOP",
            searchFilter = SearchFilter(
                types = if (instrumentType != null) {
                    listOf(instrumentType.toString().uppercase())
                } else {
                    listOf()
                }
            ),
            searchSessionId = UUID.randomUUID().toString()
        )

        val response = avanzaService.search(searchRequest).execute()
        check(response.isSuccessful) {"Search request not successful ${response.message()} body: ${response.errorBody()?.string()}"}

        return response.body()!!
    }
}

private fun Int.toTotpString(): String {
    val code = this.toString()
    check(code.length <= 6) {"Totp codes must be at most 6 character, this Int does not qualify"}
    return code.padStart(6, '0')
}