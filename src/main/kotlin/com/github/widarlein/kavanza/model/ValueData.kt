package com.github.widarlein.kavanza.model


import com.google.gson.annotations.SerializedName

data class ValueData(
    @SerializedName("decimalPrecision")
    val decimalPrecision: Int,
    @SerializedName("unit")
    val unit: String,
    @SerializedName("unitType")
    val unitType: String,
    @SerializedName("value")
    val value: Double
)