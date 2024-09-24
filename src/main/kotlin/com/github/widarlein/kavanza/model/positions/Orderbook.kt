package com.github.widarlein.kavanza.model.positions


import com.google.gson.annotations.SerializedName

data class Orderbook(
    @SerializedName("flagCode")
    val flagCode: String?,
    @SerializedName("id")
    val id: String,
    @SerializedName("lastDeal")
    val lastDeal: LastDeal,
    @SerializedName("name")
    val name: String,
    @SerializedName("quote")
    val quote: Quote,
    @SerializedName("tradeStatus")
    val tradeStatus: String,
    @SerializedName("turnover")
    val turnover: Turnover,
    @SerializedName("type")
    val type: String
)