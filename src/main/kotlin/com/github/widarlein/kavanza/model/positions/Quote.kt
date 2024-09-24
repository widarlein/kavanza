package com.github.widarlein.kavanza.model.positions


import com.github.widarlein.kavanza.model.ValueData
import com.google.gson.annotations.SerializedName

data class Quote(
    @SerializedName("buy")
    val buy: ValueData?,
    @SerializedName("change")
    val change: ValueData,
    @SerializedName("changePercent")
    val changePercent: ValueData,
    @SerializedName("highest")
    val highest: ValueData,
    @SerializedName("latest")
    val latest: ValueData,
    @SerializedName("lowest")
    val lowest: ValueData,
    @SerializedName("sell")
    val sell: ValueData?,
    @SerializedName("updated")
    val updated: String
)