package com.github.widarlein.kavanza.model.positions


import com.github.widarlein.kavanza.model.ValueData
import com.google.gson.annotations.SerializedName

data class PositionData(
    @SerializedName("account")
    val account: Account,
    @SerializedName("acquiredValue")
    val acquiredValue: ValueData,
    @SerializedName("averageAcquiredPrice")
    val averageAcquiredPrice: ValueData,
    @SerializedName("averageAcquiredPriceInstrumentCurrency")
    val averageAcquiredPriceInstrumentCurrency: ValueData,
    @SerializedName("collateralFactor")
    val collateralFactor: ValueData,
    @SerializedName("id")
    val id: String,
    @SerializedName("instrument")
    val instrument: Instrument,
    @SerializedName("lastTradingDayPerformance")
    val lastTradingDayPerformance: LastTradingDayPerformance?,
    @SerializedName("superInterestApproved")
    val superInterestApproved: Boolean,
    @SerializedName("value")
    val value: ValueData,
    @SerializedName("volume")
    val volume: ValueData
)