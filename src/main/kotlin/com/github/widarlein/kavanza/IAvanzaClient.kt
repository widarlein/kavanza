package com.github.widarlein.kavanza

import com.github.widarlein.kavanza.model.*
import com.github.widarlein.kavanza.model.getordersanddeals.GetDealsResponse
import com.github.widarlein.kavanza.model.getordersanddeals.GetOrdersResponse
import com.github.widarlein.kavanza.model.order.*
import com.github.widarlein.kavanza.model.positions.Positions

interface IAvanzaClient {
    /**
     * Get overview of the logged in user account
     */
    fun getOverview(): Overview

    /**
     * Get an overview of categories and accounts
     */
    fun getCategorizedAccountsOverview(): CategorizedAccountsOverview

    /**
     * Get the positions of the logged in user
     */
    fun getPositions(): Positions

    /**
     * Gets all orders of the logged in user
     *
     * @return a response containing the list of orders
     */
    fun getOrders(): GetOrdersResponse

    /**
     * Gets all deals of the logged in user
     *
     * @return a response containing the list of deals
     */
    fun getDeals(): GetDealsResponse

    /**
     * Gets all transactions of a specified account
     *
     * @param accountId the id of the account in question
     * @param transactionOptions options regarding the wanted transactions
     * @return the transactions
     */
    fun getTransactions(accountId: AccountId, transactionOptions: TransactionOptions? = null): Transactions

    /**
     * Gets all transactions of a specified type
     *
     * @param transactionType the type of the wanted transactions
     * @param transactionOptions options regarding the wanted transactions
     * @return the transactions
     */
    fun getTransactions(transactionType: TransactionType, transactionOptions: TransactionOptions? = null): Transactions

    /**
     * Gets all watchlists
     *
     * @return the watchlists
     */
    fun getWatchlists(): List<Watchlist>

    /**
     * Adds a specific orderbook (equity security: stock, certificate, etc) to a given watchlist
     *
     * @param watchlistId the ID of the watchlist in question
     * @param orderbookId the ID of the security
     */
    fun addToWatchlist(watchlistId: String, orderbookId: String)

    /**
     * Removes a specific orderbook (equity security: stock, certificate, etc) to a given watchlist
     *
     * @param watchlistId the ID of the watchlist in question
     * @param orderbookId the ID of the security
     */
    fun removeFromWatchlist(watchlistId: String, orderbookId: String)

    /**
     * Get details about one or more orderbooks
     *
     * @param orderbookIds a list of orderbook IDs
     * @return a list of orderbook overviews
     */
    fun getOrderbooks(orderbookIds: List<String>): List<OrderbookListItem>

    /**
     * Get details about one orderbook in the form of market guide. Data that is loaded when you visit the page
     * https://www.avanza.se/aktier/om-aktien.html/5247/investor-b on web
     *
     * @param orderbookId an orderbooks
     * @return returns market guide stock
     */
    fun getMarketGuideStock(orderbookId: String): MarketGuideStock

    /**
     * Gets details about a specified instrument
     *
     * @param instrumentType the type of instrument wanted
     * @param instrumentId the id of the instrument in question
     * @return instrument data
     */
    fun getInstrument(instrumentType: InstrumentType, instrumentId: String): InstrumentData
    fun getInspirationLists(): List<InspirationList>
    fun getInspirationLists(listType: ListType): InspirationList

    /**
     * Place an order
     *
     * @param orderOptions options about the order to place
     * @return a response indicating status and id of the order operation
     */
    fun placeOrder(orderOptions: OrderOptions): OrderOperationResponse

    /**
     * Get a specific order
     *
     * @param instrumentType the type of the security in the order
     * @param accountId the ID of the account of the order
     * @param orderId the ID of the order
     * @return the order
     */
    fun getOrder(instrumentType: InstrumentType, accountId: AccountId, orderId: String): Order

    /**
     * Delete an order
     *
     * @param accountId the ID of the account of the order
     * @param orderId the ID of the order
     * @return a response indicating status and id of the order operation
     */
    fun deleteOrder(accountId: AccountId, orderId: String): OrderOperationResponse

    /**
     * Free text search for an instrument
     *
     * @param searchQuery the text query to search for
     * @param instrumentType only search for instruments of this type. Defaults to null which searches among all types
     * @return the result of the search query
     */
    fun search(searchQuery: String, instrumentType: InstrumentType? = null): SearchResponse

    /**
     * Modify an order
     *
     * @param orderOptions options about the order to place
     * @return a response indicating status and id of the order operation
     */
    fun modifyOrder(orderOptions: OrderOptions): OrderOperationResponse

    /**
     * Place a stop loss order
     * @param orderOperation options about the stop loss order to place
     * @return a response indicating status and id of the order operation
     */
    fun placeStopLoss(orderOperation: StopLossOperation): StopLossOperationResponse

    /**
     * Delete a stop loss order
     *
     * @param accountId the ID of the account of the order
     * @param stopLossId the ID of the stop loss order
     */
    fun deleteStopLoss(accountId: AccountId, stopLossId: String)

    /**
     * Gets prices over time for a specific stock and only stock
     *
     * @param orderbookId the ID of the stock in question
     * @param period the period over which to get the price
     * @param resolution the resolution of the price data
     * @return chart data {@link com.github.widarlein.kavanza.model.PriceChart}
     */
    fun getStockPriceChart(orderbookId: String, period: TimePeriod, resolution: ResolutionType = ResolutionType.DAY): PriceChart

    /**
     * Gets exchange rate between two currencies
     *
     * @param fromCurrency the currency to convert from
     * @param toCurrency the currency to convert to
     *
     * @return the exchange rate, i.e. the number to multiply with to convert from fromCurrency to toCurrency
     */
    fun getExchangeRate(fromCurrency: String, toCurrency: String): ExchangeRate
}