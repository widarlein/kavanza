package com.github.widarlein.kavanza.model.positions


import com.github.widarlein.kavanza.model.ValueData
import com.google.gson.annotations.SerializedName

data class Turnover(
    @SerializedName("value")
    val value: ValueData?,
    @SerializedName("volume")
    val volume: ValueData
)