package com.github.widarlein.kavanza.model.getordersanddeals


import com.google.gson.annotations.SerializedName

data class GetDealsResponse(
    @SerializedName("deals")
    val deals: List<Deal>,
    @SerializedName("fundDeals")
    val fundDeals: List<Any?>
)

data class Deal(
    @SerializedName("account")
    val account: Account,
    @SerializedName("amount")
    val amount: Double,
    @SerializedName("id")
    val id: String,
    @SerializedName("orderId")
    val orderId: String,
    @SerializedName("orderbook")
    val orderbook: Orderbook,
    @SerializedName("orderbookId")
    val orderbookId: String,
    @SerializedName("price")
    val price: Double,
    @SerializedName("side")
    val side: String,
    @SerializedName("time")
    val time: String,
    @SerializedName("volume")
    val volume: Int
)