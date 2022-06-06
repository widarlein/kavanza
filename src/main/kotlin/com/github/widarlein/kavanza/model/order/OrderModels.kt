package com.github.widarlein.kavanza.model.order

import com.github.widarlein.kavanza.model.AccountId
import com.github.widarlein.kavanza.model.BrokerTradeSummary
import com.github.widarlein.kavanza.model.LatestTrades

enum class OrderSide {
    BUY,
    SELL
}

/**
 * A class representing options to specify when placing and order
 */
data class OrderOptions(
    /**
     * The ID of the account the order should be placed on
     */
    val accountId: AccountId,

    /**
     *  The ID of the instrument to place an order for
     */
    val orderbookId: String,

    /**
     * Type of order, BUY or SELL
     */
    val side: OrderSide,

    /**
     * The price limit of the order
     */
    val price: Double,

    /**
     * Order is valid until this date.
     *
     * The format of the date is yyyy-MM-dd
     */
    // TODO: 2019-09-09 deserialize from Date class?
    val validUntil: String,

    /**
     * The desired volume of the security to trade
     */
    val volume: Int,

    /**
     * Conditions of the order..? I don't really know
     */
    val condition: String = "NORMAL",

    /**
     * Avanza may have some automatic reinvestment of dividends? Idk but for normal orders, it's false.
     */
    val isDividendReinvestment: Boolean = false,

    //val openVolume

    /**
     * The ID of the request you send as an UUID
     */
    val requestId: String,

    /**
     * If these options are used to modify an order, the order ID of said order needs to be set
     */
    val orderId: String? = null

    )

data class OrderOperationResponse (
    val message: String,
    val orderId: String,
    val orderRequestStatus: String
)

data class Order (
    val customer: Customer,
    val account: Account,
    val orderbook: Orderbook,
    val firstTradableDate: String,
    val lastTradableDate: String,
    val untradableDates: List<String>,
    val orderDepthLevels: List<String>,
    val marketMakerExpected: Boolean,
    val orderDepthReceivedTime: String,
    val latestTrades: List<LatestTrades>,
    val marketTrades: Boolean,
    val hasShortSellKnowledge: Boolean,
    val hasInstrumentKnowledge: Boolean,
    val brokerTradeSummary: BrokerTradeSummary,
    val hasInvestmentFees: HasInvestmentFees,
    val tickSizeRules: List<TickSizeRules>,
    val order: Order
)

data class Customer (
    val showCourtageClassInfoOnOrderPage: Boolean,
    val courtageClass: String
)

data class Account (
    val type: String,
    val name: Int,
    val id: Int,
    val totalBalance: Double,
    val buyingPower: Double
)

data class Orderbook (
    val highestPrice: Double,
    val lowestPrice: Double,
    val lastPrice: Double,
    //TODO examine if it can be serialized as Date (or other Date class)
    //timestamp in the format 2019-08-30T22:00:00.496+0200
    val lastPriceUpdated: String,
    val change: Double,
    val changePercent: Double,
    val totalVolumeTraded: Int,
    val totalValueTraded: Double,
    val exchangeRate: Double,
    val flagCode: String,
    val positionVolume: Int,
    val name: String,
    val id: String,
    val type: String,
    val currency: String,
    val tradable: Boolean,
    val tickerSymbol: String,
    val tradingUnit: Int,
    val volumeFactor: Double
)

data class HasInvestmentFees (
    val buy: Boolean,
    val sell: Boolean
)

data class TickSizeRules (
    val minPrice: Double,
    val maxPrice: Double,
    val tickSize: Double
)