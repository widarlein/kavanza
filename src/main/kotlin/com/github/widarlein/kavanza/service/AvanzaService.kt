package com.github.widarlein.kavanza.service

import com.github.widarlein.kavanza.model.*
import com.github.widarlein.kavanza.model.getordersanddeals.GetDealsResponse
import com.github.widarlein.kavanza.model.getordersanddeals.GetOrdersResponse
import com.github.widarlein.kavanza.model.order.*
import com.github.widarlein.kavanza.model.positions.Positions
import retrofit2.Call
import retrofit2.http.*


interface AvanzaService {

    @POST("/_api/authentication/sessions/usercredentials")
    fun login(@Body userCredentials: UserCredentials): Call<LoginResponse>

    /**
     * Second part of the login procedure providing the TOTP code
     *
     * After login has been called it will require to send in a TOTP code generated from a pre-configured secret.
     * The first login call also returns a transaction id which needs to be provided as a cookie
     *
     * @param cookie the cookie with the transaction id. Needs to be formatted as "AZAMFATRANSACTION=%s" where %s is the id
     * @param totpRequest the model with the 2FA method (default "TOTP" which is the only supported one) and totp code
     * @return the response if the request was successful
     */
    @POST("/_api/authentication/sessions/totp")
    fun totp(@Header("Cookie") cookie: String, @Body totpRequest: TotpRequest): Call<TotpResponse>

    /**
     * Gets an overview over all accounts
     */
    @GET("/_mobile/account/overview")
    fun getOverview(): Call<Overview>

    @GET("/_api/account-overview/overview/categorizedAccounts")
    fun getCategorizedAccountsOverview(): Call<CategorizedAccountsOverview>

    /**
     * Gets all current positions
     */
    @GET("/_api/position-data/positions")
    fun getPostions(): Call<Positions>

    /**
     * Gets all orders of the logged in user
     */
    @GET("/_api/trading/rest/orders")
    fun getOrders(): Call<GetOrdersResponse>

    /**
     * Gets all deals of the logged in user
     */
    @GET("/_api/trading/rest/deals")
    fun getDeals(): Call<GetDealsResponse>

    /**
     * Gets all transactions of an account
     *
     * @param accountOrTransactionType a valid account id or a transaction type
     * @param options a map containing all query parameter options ad in {@link com.github.widarlein.kavanza.model.TransactionOptions}
     */
    @GET("/_mobile/account/transactions/{accountOrTransactionType}")
    fun getTransactions(@Path("accountOrTransactionType") accountOrTransactionType: String, @QueryMap options: Map<String, String>): Call<Transactions>

    /**
     * Gets watchlists of account
     */
    @GET("/_mobile/usercontent/watchlist")
    fun getWatchlists(): Call<List<Watchlist>>

    /**
     * Adds a specific orderbook (equity security: stock, certificate, etc) to a given watchlist
     */
    @PUT("/_api/usercontent/watchlist/{watchlistId}/orderbooks/{orderbookId}")
    fun addToWatchlist(@Path("watchlistId") watchlistId: String, @Path("orderbookId") orderbookId: String): Call<Void>

    /**
     * Removes a specific orderbook (equity security: stock, certificate, etc) to a given watchlist
     */
    @DELETE("/_api/usercontent/watchlist/{watchlistId}/orderbooks/{orderbookId}")
    fun removeFromWatchlist(@Path("watchlistId") watchlistId: String, @Path("orderbookId") orderbookId: String): Call<Void>

    /**
     * Get details about one or more orderbooks
     *
     * @param orderbookIds a single or comma separated list of orderbooks
     * @return a call which, if successful, returns a list of orderbook overviews
     */
    @GET("/_mobile/market/orderbooklist/{orderbookIds}?sort=name")
    fun getOrderbooks(@Path("orderbookIds") orderbookIds: String): Call<List<OrderbookListItem>>

    /**
     * Get details about one orderbook in the form of market guide. Data that is loaded when you visit the page
     * https://www.avanza.se/aktier/om-aktien.html/5247/investor-b on web
     *
     * @param orderbookId an orderbooks
     * @return a call which, if successful, returns market guide stock
     */
    @GET("https://www.avanza.se/_api/market-guide/stock/{orderbookId}")
    fun getMarketGuideStock(@Path("orderbookId") orderbookId: String): Call<MarketGuideStock>

    //TODO
    @GET("api/trading-critical/rest/orderbook/{orderbookId}")
    fun getOrderbook(@Path("orderbookId") orderbookId: String): Call<Any>

    /**
     * Gets prices over time for a specific stock and only stock.
     *
     * @param orderbookId the ID of the stock in question
     * @param period the period over which to get the price
     * @return a call which, if successful, returns chart data
     */
    @GET("_api/price-chart/stock/{orderbookId}")
    fun getStockPriceChart(@Path("orderbookId") orderbookId: String,
                           @Query("timePeriod") period: TimePeriod,
                           @Query("resolution") resolution: ResolutionType,
                           ): Call<PriceChart>

    /**
     * Gets details about a specified instrument
     *
     * @param instrumentType the type of instrument wanted
     * @param instrumentId the id of the instrument in question
     * @return a call which, if successful, returns a details about the specified instrument
     */
    @GET("/_mobile/market/{instrumentType}/{instrumentId}")
    fun getInstrument(@Path("instrumentType") instrumentType: InstrumentType, @Path("instrumentId") instrumentId: String): Call<InstrumentData>

    /**
     * Gets a list of all inspiration lists
     */
    // FIXME There is a bug here. The data model only works for when the orderbooks list of the Inspiration list are of the type STOCK
    // this is an inherent bug stemming from the fact that the json returned contains different "types" depending on what
    // ´instrumentType´ the inspiration list has. The current implementation only supports instrumentType=STOCK
    @GET("/_mobile/marketing/inspirationlist")
    fun getInspirationLists(): Call<List<InspirationList>>

    /**
     * Gets a list of inspiration lists of a certain type
     *
     * @param listType the type of inspiration lists wanted
     */
    // FIXME The data model returned by this call only supports Inspiration lists of the type STOCK
    // the available listType parameters (see ListType in InspirationModels) are only of type FUND, making this service
    // endpoint virtually useless.
    @GET("/_mobile/marketing/inspirationlist/{listType}")
    fun getInspirationList(@Path("listType") listType: ListType): Call<InspirationList>

    /**
     * Place an order
     *
     * @param orderOptions options about the order to place
     */
    @POST("/_api/trading-critical/rest/order/new")
    fun placeOrder(@Body orderOptions: OrderOptions): Call<OrderOperationResponse>

    /**
     * Get a specific order
     *
     * @param instrumentType the type of the security in the order
     * @param accountId the ID of the account of the order
     * @param orderId the ID of the order
     * @return a call with the order as body
     */
    @Deprecated("Doesn't work, don't know the replacement")
    @GET("/_mobile/order/{instrumentType}")
    fun getOrder(
        @Path("instrumentType") instrumentType: InstrumentType,
        @Query("accountId") accountId: String,
        @Query("orderId") orderId: String
    ): Call<Order>

    /**
     * Modify an existing order.
     *
     * @param orderOptions Same kind of options as when placing an order, but make sure to set orderId
     */
    @POST("/_api/trading-critical/rest/order/modify")
    fun modifyOrder(@Body orderOptions: OrderOptions): Call<OrderOperationResponse>


    /**
     * Delete an order
     *
     * @param accountId the ID of the account of the order
     * @param orderId the ID of the order
     */
    @POST("/_api/trading-critical/rest/order/delete")
    fun deleteOrder(@Body orderOptions: DeleteOrderOperation): Call<OrderOperationResponse>

    /**
     * Place a stop loss order
     *
     * @param orderOptions options about the stop loss order to place
     * @return a response indicating status and id of the order operation
     */
    @POST("_api/trading/stoploss/new")
    fun placeStopLossOrder(@Body orderOptions: StopLossOperation): Call<StopLossOperationResponse>

    /**
     * Delete a stop loss order
     */
    @DELETE("_api/trading/stoploss/{accountId}/{stopLossId}")
    fun deleteStopLoss(@Path("accountId") accountId: String, @Path("stopLossId") stopLossId: String): Call<Unit>

    /**
     * Free text search for an instrument
     *
     * @param searchRequest The payload for the search request
     * @return the result of the search query
     */
    @POST("/_api/search/filtered-search")
    fun search(@Body searchRequest: SearchRequest): Call<SearchResponse>

    /**
     * Get exchange rate between two currencies
     *
     * @param currencyFrom the currency to convert from
     * @param currencyTo the currency to convert to
     * @return the exchange rate
     */
    @GET("/_api/trading/rest/exchangerate/{currencyFrom}/{currencyTo}")
    fun getExchangeRate(@Path("currencyFrom") currencyFrom: String, @Path("currencyTo") currencyTo: String): Call<ExchangeRate>

}