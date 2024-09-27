package com.github.widarlein.kavanza.model


import com.google.gson.annotations.SerializedName

data class PriceChart(
    @SerializedName("from")
    val from: String,
    @SerializedName("metadata")
    val metadata: Metadata,
    @SerializedName("ohlc")
    val ohlc: List<Ohlc>,
    @SerializedName("previousClosingPrice")
    val previousClosingPrice: Double,
    @SerializedName("to")
    val to: String
)

data class Metadata(
    @SerializedName("resolution")
    val resolution: Resolution
)

data class Resolution(
    @SerializedName("availableResolutions")
    val availableResolutions: List<String>,
    @SerializedName("chartResolution")
    val chartResolution: String
)

data class Ohlc(
    @SerializedName("close")
    val close: Double,
    @SerializedName("high")
    val high: Double,
    @SerializedName("low")
    val low: Double,
    @SerializedName("open")
    val `open`: Double,
    @SerializedName("timestamp")
    val timestamp: Long,
    @SerializedName("totalVolumeTraded")
    val totalVolumeTraded: Int
)

enum class TimePeriod {
    TODAY,
    ONE_MONTH,
    THREE_MONTHS,
    ONE_WEEK,
    THIS_YEAR,
    ONE_YEAR,
    THREE_YEARS,
    FIVE_YEARS,
    INFINITY;

    override fun toString() = super.toString().lowercase()
}

enum class ResolutionType {
    DAY,
    WEEK,
    MONTH;

    override fun toString() = super.toString().lowercase()
}