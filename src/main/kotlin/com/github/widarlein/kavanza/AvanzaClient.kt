package com.github.widarlein.kavanza

import com.eatthepath.otp.TimeBasedOneTimePasswordGenerator
import com.github.widarlein.kavanza.model.*
import com.github.widarlein.kavanza.model.order.Order
import com.github.widarlein.kavanza.model.order.OrderOptions
import com.github.widarlein.kavanza.model.order.OrderOperationResponse
import com.github.widarlein.kavanza.service.AvanzaService
import com.github.widarlein.kavanza.service.HeaderInterceptor
import com.github.widarlein.kavanza.service.LoggingInterceptor
import com.google.common.io.BaseEncoding
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.RuntimeException
import java.time.Instant
import javax.crypto.spec.SecretKeySpec

private const val BASE_URL = "https://www.avanza.se/"
private const val TRANSACTION_COOKIE = "AZAMFATRANSACTION="

object AvanzaClient {

    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .client(
            OkHttpClient.Builder()
                .addInterceptor(HeaderInterceptor)
//                .addInterceptor(LoggingInterceptor)
                .build()
        )
        .build()

    private val avanzaService: AvanzaService = retrofit.create(AvanzaService::class.java)

    internal fun login(userCredentials: UserCredentials, totpSecret: String) {
        val loginCall: Call<LoginResponse> = avanzaService.login(userCredentials)
        val loginCallResponse = loginCall.execute()
        if (!loginCallResponse.isSuccessful) {
            throw RuntimeException("Could not perform login procedure response was ${loginCallResponse.message()} body: ${loginCallResponse.errorBody()}")
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
            HeaderInterceptor.authenticationHeaders = HeaderInterceptor.AuthenticationHeaders(
                totpResponse.authenticationSession,
                totpCallResponse.headers()["X-SecurityToken"]!!
            )
        } else {
            throw RuntimeException("Could not perform login totp procedure response was ${totpCallResponse.message()} body: ${totpCallResponse.errorBody()}")
        }

    }

    /**
     * Get overview of the logged in user account
     */
    fun getOverview(): Overview {
        val response = avanzaService.getOverview().execute()
        check(response.isSuccessful) {"Overview request not successful ${response.message()} body: ${response.errorBody()}"}
        return response.body()!!
    }

    /**
     * Get overview of a specific Avanza account
     */
    fun getAccountOverview(accountId: AccountId): AccountOverview {
        val response = avanzaService.getAccountOverview(accountId.value).execute()
        check(response.isSuccessful) {"Account overview request not successful ${response.message()} body: ${response.errorBody()}"}
        return response.body()!!
    }

    /**
     * Get the positions of the logged in user
     */
    fun getPositions(): PositionModel {
        val response = avanzaService.getPostions().execute()
        check(response.isSuccessful) { "Positions request not successful ${response.message()} body: ${response.errorBody()}" }
        return response.body()!!
    }

    /**
     * Get deals and recent orders made by the user
     */
    fun getDealsAndOrders(): DealsAndOrders {
        val response = avanzaService.getDealsAndOrders().execute()
        check(response.isSuccessful) { "Deals and Order request not successful ${response.message()} body: ${response.errorBody()}" }
        return response.body()!!
    }

    /**
     * Gets all transactions of a specified account
     *
     * @param accountId the id of the account in question
     * @param transactionOptions options regarding the wanted transactions
     * @return the transactions
     */
    fun getTransactions(accountId: AccountId, transactionOptions: TransactionOptions? = null) = getTransactions(accountId.value, transactionOptions)

    /**
     * Gets all transactions of a specified type
     *
     * @param transactionType the type of the wanted transactions
     * @param transactionOptions options regarding the wanted transactions
     * @return the transactions
     */
    fun getTransactions(transactionType: TransactionType, transactionOptions: TransactionOptions? = null) = getTransactions(transactionType.transactionType, transactionOptions)


    private fun getTransactions(accountOrTransactionType: String, transactionOptions: TransactionOptions? = null): Transactions {
        val queryMap = if (transactionOptions != null) {
            transactionOptions.toQueryMap()
        } else {
            mapOf()
        }

        val response = avanzaService.getTransactions(accountOrTransactionType, queryMap).execute()
        check(response.isSuccessful) {"Transactions request not successful ${response.message()} body: ${response.errorBody()}"}
        return response.body()!!
    }

    /**
     * Gets all watchlists
     *
     * @return the watchlists
     */
    fun getWatchlists(): List<Watchlist> {
        val response = avanzaService.getWatchlists().execute()
        check(response.isSuccessful) {"Watchlist request not successful ${response.message()} body: ${response.errorBody()}"}
        return response.body()!!
    }

    /**
     * Adds a specific orderbook (equity security: stock, certificate, etc) to a given watchlist
     *
     * @param watchlistId the ID of the watchlist in question
     * @param orderbookId the ID of the security
     */
    fun addToWatchlist(watchlistId: String, orderbookId: String) {
        val response = avanzaService.addToWatchlist(watchlistId, orderbookId).execute()
        check(response.isSuccessful) {"Add to watchlist request not successful ${response.message()} body: ${response.errorBody()}"}
    }

    /**
     * Removes a specific orderbook (equity security: stock, certificate, etc) to a given watchlist
     *
     * @param watchlistId the ID of the watchlist in question
     * @param orderbookId the ID of the security
     */
    fun removeFromWatchlist(watchlistId: String, orderbookId: String) {
        val response = avanzaService.removeFromWatchlist(watchlistId, orderbookId).execute()
        check(response.isSuccessful) {"Remove from watchlist request not successful ${response.message()} body: ${response.errorBody()}"}
    }

    /**
     * Get details about one or more orderbooks
     *
     * @param orderbookIds a list of orderbook IDs
     * @return a list of orderbook overviews
     */
    fun getOrderbooks(orderbookIds: List<String>): List<OrderbookListItem> {
        val response = avanzaService.getOrderbooks(orderbookIds.joinToString(",")).execute()
        check(response.isSuccessful) {"Orderbook list request not successful ${response.message()} body: ${response.errorBody()}"}
        return response.body()!!
    }

    /**
     * Gets prices over time for a specific orderbook
     *
     * @param orderbookId the ID of the orderbook in question
     * @param period the period over which to get the price
     * @return chart data {@link com.github.widarlein.kavanza.model.ChartData}
     */
    fun getChartData(orderbookId: String, period: Period): ChartData {
        val response = avanzaService.getChartData(orderbookId, period).execute()
        check(response.isSuccessful) {"Chart data request not successful ${response.message()} body: ${response.errorBody()}"}
        return response.body()!!
    }

    /**
     * Gets details about a specified instrument
     *
     * @param instrumentType the type of instrument wanted
     * @param instrumentId the id of the instrument in question
     * @return instrument data
     */
    fun getInstrument(instrumentType: InstrumentType, instrumentId: String): InstrumentData {
        val response = avanzaService.getInstrument(instrumentType, instrumentId).execute()
        check(response.isSuccessful) {"Instrument details request not successful ${response.message()} body: ${response.errorBody()}"}
        return response.body()!!
    }

    fun getInspirationLists(): List<InspirationList> {
        TODO("Endpoint not returning reliable data. See FIXME in related AvanzaService.kt endpoint")
        val response = avanzaService.getInspirationLists().execute()
        check(response.isSuccessful) {"Inspiration lists request not successful ${response.message()} body: ${response.errorBody()}"}
        return response.body()!!
    }

    fun getInspirationLists(listType: ListType): InspirationList {
        TODO("Endpoint not returning reliable data. See FIXME in related AvanzaService.kt endpoint")
        val response = avanzaService.getInspirationList(listType).execute()
        check(response.isSuccessful) {"Inspiration list request not successful ${response.message()} body: ${response.errorBody()}"}
        return response.body()!!
    }

    /**
     * Place an order
     *
     * @param orderOptions options about the order to place
     * @return a response indicating status and id of the order operation
     */
    fun placeOrder(orderOptions: OrderOptions): OrderOperationResponse {
        val response = avanzaService.placeOrder(orderOptions).execute()
        check(response.isSuccessful) {"Place order request not successful ${response.message()} body: ${response.errorBody()}"}
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
    fun getOrder(instrumentType: InstrumentType, accountId: AccountId, orderId: String): Order {
        val response = avanzaService.getOrder(instrumentType, accountId.value, orderId).execute()
        check(response.isSuccessful) {"Edit order request not successful ${response.message()} body: ${response.errorBody()}"}
        return response.body()!!
    }

    /**
     * Edit an order
     *
     * @param instrumentType the type of the security in the order
     * @param orderId the ID of the order
     * @param orderOptions options about the order to place
     * @return a response indicating status and id of the order operation
     */
    fun editOrder(instrumentType: InstrumentType, orderId: String, orderOptions: OrderOptions): OrderOperationResponse {
        val response = avanzaService.editOrder(instrumentType, orderId, orderOptions).execute()
        check(response.isSuccessful) {"Edit order request not successful ${response.message()} body: ${response.errorBody()}"}
        return response.body()!!
    }

    /**
     * Delete an order
     *
     * @param accountId the ID of the account of the order
     * @param orderId the ID of the order
     * @return a response indicating status and id of the order operation
     */
    fun deleteOrder(accountId: AccountId, orderId: String): OrderOperationResponse {
        val response = avanzaService.deleteOrder(accountId.value, orderId).execute()
        check(response.isSuccessful) {"Delete order request not successful ${response.message()} body: ${response.errorBody()}"}
        return response.body()!!
    }


    /**
     * Free text search for an instrument
     *
     * @param searchQuery the text query to search for
     * @param instrumentType only search for instruments of this type. Defaults to NONE which searches among all types
     * @return the result of the search query
     */
    fun search(searchQuery: String, instrumentType: InstrumentType = InstrumentType.NONE): SearchHits {
        val response = avanzaService.search(instrumentType, searchQuery, 100).execute()
        check(response.isSuccessful) {"Search request not successful ${response.message()} body: ${response.errorBody()}"}
        return response.body()!!
    }
}

private fun Int.toTotpString(): String {
    val code = this.toString()
    check(code.length <= 6) {"Totp codes must be at most 6 character, this Int does not qualify"}
    return code.padStart(6, '0')
}