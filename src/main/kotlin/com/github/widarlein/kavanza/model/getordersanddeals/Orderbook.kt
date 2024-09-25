package com.github.widarlein.kavanza.model.getordersanddeals


import com.google.gson.annotations.SerializedName

data class Orderbook(
    @SerializedName("countryCode")
    val countryCode: String,
    @SerializedName("currency")
    val currency: String,
    @SerializedName("id")
    val id: String,
    @SerializedName("instrumentType")
    val instrumentType: String,
    @SerializedName("isin")
    val isin: String,
    @SerializedName("mic")
    val mic: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("volumeFactor")
    val volumeFactor: String
)