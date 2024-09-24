package com.github.widarlein.kavanza.model.positions


import com.google.gson.annotations.SerializedName

data class Instrument(
    @SerializedName("currency")
    val currency: String,
    @SerializedName("id")
    val id: String,
    @SerializedName("isin")
    val isin: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("orderbook")
    val orderbook: Orderbook,
    @SerializedName("type")
    val type: String,
    @SerializedName("volumeFactor")
    val volumeFactor: Double
)