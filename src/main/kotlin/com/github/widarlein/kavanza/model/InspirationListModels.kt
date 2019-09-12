package com.github.widarlein.kavanza.model

enum class ListType {
    HIGHEST_RATED_FUNDS,
    LOWEST_FEE_INDEX_FUNDS,
    BEST_DEVELOPMENT_FUNDS_LAST_THREE_MONTHS,
    MOST_OWNED_FUNDS;
}

data class InspirationList (
    val orderbooks: List<InspirationOrderbook>,
    val averageChange: Double,
    val name: String,
    val id: String,
    val highlightField: HighlightField,
    val averageChangeSinceThreeMonths: Double,
    val imageUrl: String,
    val information: String,
    val statistics: Statistics,
    val instrumentType: String
)

data class InspirationOrderbook (
    val priceThreeMonthsAgo: Double,
    val currency: String,
    val priceOneYearAgo: Double,
    val lastPrice: Double,
    val change: Double,
    val changePercent: Double,
    //TODO examine if it can be serialized as Date (or other Date class)
    //timestamp in the format 2019-08-30T22:00:00.496+0200
    val updated: String,
    val name: String,
    val id: String,
    val highlightValue: Double,
    val flagCode: String
)

data class HighlightField (
    val label: String,
    val percent: Boolean
)

data class Statistics (
    val positiveCount: Int,
    val neutralCount: Int,
    val negativeCount: Int,
    val neutralPercent: Double,
    val negativePercent: Double,
    val positivePercent: Double
)