package com.github.widarlein.kavanza.model.positions


import com.github.widarlein.kavanza.model.ValueData
import com.google.gson.annotations.SerializedName

data class LastTradingDayPerformance(
    @SerializedName("absolute")
    val absolute: ValueData,
    @SerializedName("relative")
    val relative: ValueData
)