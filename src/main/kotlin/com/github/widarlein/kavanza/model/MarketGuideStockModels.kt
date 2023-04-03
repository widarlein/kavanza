package com.github.widarlein.kavanza.model


import com.google.gson.annotations.SerializedName

data class MarketGuideStock(
    @SerializedName("historicalClosingPrices")
    val historicalClosingPrices: HistoricalClosingPrices,
    @SerializedName("instrumentId")
    val instrumentId: String,
    @SerializedName("isin")
    val isin: String,
    @SerializedName("keyIndicators")
    val keyIndicators: KeyIndicators,
    @SerializedName("listing")
    val listing: Listing,
    @SerializedName("name")
    val name: String,
    @SerializedName("orderbookId")
    val orderbookId: String,
    @SerializedName("quote")
    val quote: Quote,
    @SerializedName("sectors")
    val sectors: List<Sector>,
    @SerializedName("tradable")
    val tradable: String,
    @SerializedName("type")
    val type: String
)

data class HistoricalClosingPrices(
    @SerializedName("fiveYears")
    val fiveYears: Double,
    @SerializedName("oneDay")
    val oneDay: Double,
    @SerializedName("oneMonth")
    val oneMonth: Double,
    @SerializedName("oneWeek")
    val oneWeek: Double,
    @SerializedName("oneYear")
    val oneYear: Double,
    @SerializedName("start")
    val start: Double,
    @SerializedName("startDate")
    val startDate: String,
    @SerializedName("startOfYear")
    val startOfYear: Double,
    @SerializedName("tenYears")
    val tenYears: Double,
    @SerializedName("threeMonths")
    val threeMonths: Double,
    @SerializedName("threeYears")
    val threeYears: Double
)

data class KeyIndicators(
    @SerializedName("beta")
    val beta: Double,
    @SerializedName("capitalTurnover")
    val capitalTurnover: Double,
    @SerializedName("directYield")
    val directYield: Double,
    @SerializedName("dividend")
    val dividend: Dividend,
    @SerializedName("dividendsPerYear")
    val dividendsPerYear: Int,
    @SerializedName("earningsPerShare")
    val earningsPerShare: EarningsPerShare,
    @SerializedName("equityPerShare")
    val equityPerShare: EquityPerShare,
    @SerializedName("equityRatio")
    val equityRatio: Double,
    @SerializedName("marketCapital")
    val marketCapital: MarketCapital,
    @SerializedName("nextReport")
    val nextReport: NextReport,
    @SerializedName("numberOfOwners")
    val numberOfOwners: Int,
    @SerializedName("previousReport")
    val previousReport: PreviousReport,
    @SerializedName("priceEarningsRatio")
    val priceEarningsRatio: Double,
    @SerializedName("reportDate")
    val reportDate: String,
    @SerializedName("returnOnEquity")
    val returnOnEquity: Double,
    @SerializedName("returnOnTotalAssets")
    val returnOnTotalAssets: Double,
    @SerializedName("turnoverPerShare")
    val turnoverPerShare: TurnoverPerShare,
    @SerializedName("volatility")
    val volatility: Double
) {
    data class Dividend(
        @SerializedName("amount")
        val amount: Double,
        @SerializedName("currencyCode")
        val currencyCode: String,
        @SerializedName("exDate")
        val exDate: String,
        @SerializedName("exDateStatus")
        val exDateStatus: String,
        @SerializedName("paymentDate")
        val paymentDate: String
    )

    data class EarningsPerShare(
        @SerializedName("currency")
        val currency: String,
        @SerializedName("value")
        val value: Double
    )

    data class EquityPerShare(
        @SerializedName("currency")
        val currency: String,
        @SerializedName("value")
        val value: Double
    )

    data class MarketCapital(
        @SerializedName("currency")
        val currency: String,
        @SerializedName("value")
        val value: Double
    )

    data class NextReport(
        @SerializedName("date")
        val date: String,
        @SerializedName("reportType")
        val reportType: String
    )

    data class PreviousReport(
        @SerializedName("date")
        val date: String,
        @SerializedName("reportType")
        val reportType: String
    )

    data class TurnoverPerShare(
        @SerializedName("currency")
        val currency: String,
        @SerializedName("value")
        val value: Double
    )
}

data class Listing(
    @SerializedName("countryCode")
    val countryCode: String,
    @SerializedName("currency")
    val currency: String,
    @SerializedName("marketListName")
    val marketListName: String,
    @SerializedName("marketPlaceCode")
    val marketPlaceCode: String,
    @SerializedName("marketPlaceName")
    val marketPlaceName: String,
    @SerializedName("marketTradesAvailable")
    val marketTradesAvailable: Boolean,
    @SerializedName("shortName")
    val shortName: String,
    @SerializedName("tickSizeListId")
    val tickSizeListId: String,
    @SerializedName("tickerSymbol")
    val tickerSymbol: String
)

data class Quote(
    @SerializedName("change")
    val change: Double,
    @SerializedName("changePercent")
    val changePercent: Double,
    @SerializedName("highest")
    val highest: Double,
    @SerializedName("last")
    val last: Double,
    @SerializedName("lowest")
    val lowest: Double,
    @SerializedName("timeOfLast")
    val timeOfLast: Long,
    @SerializedName("totalValueTraded")
    val totalValueTraded: Double,
    @SerializedName("totalVolumeTraded")
    val totalVolumeTraded: Int,
    @SerializedName("updated")
    val updated: Long,
    @SerializedName("volumeWeightedAveragePrice")
    val volumeWeightedAveragePrice: Double
)

data class Sector(
    @SerializedName("sectorId")
    val sectorId: String,
    @SerializedName("sectorName")
    val sectorName: String
)