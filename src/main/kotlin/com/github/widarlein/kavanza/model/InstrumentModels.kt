package com.github.widarlein.kavanza.model

import com.google.gson.annotations.SerializedName

enum class InstrumentType {
    STOCK,
    FUND,
    BOND,
    OPTION,
    FUTURE_FORWARD,
    CERTIFICATE,
    WARRANT,
    EXCHANGE_TRADED_FUND,
    INDEX,
    PREMIUM_BOND,
    SUBSCRIPTION_OPTION,
    EQUITY_LINKED_BOND,
    CONVERTIBLE,
    NONE{
        override fun toString() = ""
    };

    override fun toString() = super.toString().toLowerCase()
}

data class InstrumentData (
    val priceOneWeekAgo: Double,
    val priceOneMonthAgo: Double,
    val priceThreeMonthsAgo: Double,
    val priceSixMonthsAgo: Double,
    val priceAtStartOfYear: Double,
    val priceOneYearAgo: Double,
    val priceThreeYearsAgo: Double?,
    val priceFiveYearsAgo: Double?,
    val marketPlace: String,
    val marketList: String?,
    val name: String,
    val id: String,
    val country: String,
    val currency: String,
    val shortSellable: Boolean,
    val isin: String,
    val tradable: Boolean,
    val buyPrice: Double,
    val sellPrice: Double,
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
    val loanFactor: Double,
    val flagCode: String,
    val tickerSymbol: String,
    //TODO examine if it can be serialized as Date (or other Date class)
    //timestamp in the format 2019-08-30T22:00:00.496+0200
    val quoteUpdated: String,
    val hasInvestmentFees: Boolean,
    val keyRatiosRatios: KeyRatio,
    val numberOfOwners: Int,
    val superLoan: Boolean,
    val numberOfPriceAlerts: Int?,
    val pushPermitted: Boolean,
    val dividends: List<Dividends>,
    val relatedStocks: List<RelatedStock>,
    val company: Company,
    val orderDepthLevels: List<OrderDepthLevel>,
    val marketMakerExpected: Boolean,
    val orderDepthReceivedTime: String,
    val latestTrades: List<LatestTrades>,
    val marketTrades: Boolean,
    val positions: List<Positions>?,
    val positionsTotalValue: Double?,
    val annualMeetings: List<AnnualMeetings>,
    val companyReports: List<CompanyReport>,
    val brokerTradeSummary: BrokerTradeSummary,
    val companyOwners: CompanyOwners?
)

data class KeyRatio (
    val volatility: Double,
    val priceEarningsRatio: Double,
    val directYield: Double
)

data class Dividends (
    val exDate: String,
    val amountPerShare: Double,
    val paymentDate: String,
    val currency: String
)

data class RelatedStock (
    val name: String,
    val id: String,
    val lastPrice: Double,
    val priceOneYearAgo: Double,
    val flagCode: String
)

data class Company (
    val sector: String,
    val stocks: List<Stock>,
    val chairman: String,
    val totalNumberOfShares: Int,
    val name: String,
    val id: String,
    val description: String,
    val marketCapital: Long,
    val marketCapitalCurrency: String,
    @SerializedName("CEO") val ceo: String
)

data class OrderDepthLevel (
    val buy: BuySellItem,
    val sell: BuySellItem
)

data class LatestTrades (
    val cancelled: Boolean,
    val price: Double,
    val volume: Int,
    //TODO examine if it can be serialized as Date (or other Date class)
    //timestamp in the format 2019-08-30T22:00:00.496+0200
    val dealTime: String,
    val matchedOnMarket: Boolean
)

data class AnnualMeetings (
    // date in the format of 2019-04-29
    // TODO: 2019-09-01 serialize as Date class?
    val eventDate: String,
    val extra: Boolean
)

data class CompanyReport (
    val reportType: String,
    // date in the format of 2019-04-29
    // TODO: 2019-09-01 serialize as Date class?
    val eventDate: String
)

data class BrokerTradeSummary (
    val orderbookId: String,
    val items: List<BrokerTradeItem>
)

data class CompanyOwners (
    val list: List<CompanyOwner>,
    // date in the format of 2019-04-29
    // TODO: 2019-09-09 serialize as Date class?
    val updated: String
)

data class CompanyOwner (
    val name: String,
    val capital: Double,
    val votes: Double
)

data class Stock (
    val totalNumberOfShares: Int,
    val name: String
)

data class BuySellItem (
    val percent: Double,
    val price: Double,
    val volume: Int
)

data class BrokerTradeItem (
    val buyVolume: Int,
    val sellVolume: Int,
    val brokerCode: String,
    val netBuyVolume: Int
)