package com.github.widarlein.kavanza.model.getorders


import com.google.gson.annotations.SerializedName

data class GetOrdersResponse(
    // Unsure how fundorders are represented in the API so leaving it as Any for now
    @SerializedName("fundOrders")
    val fundOrders: List<Any?>,
    @SerializedName("orders")
    val orders: List<Order>
)

data class Order(
    @SerializedName("account")
    val account: Account,
    @SerializedName("additionalParameters")
    val additionalParameters: Any?,
    @SerializedName("amount")
    val amount: Double,
    @SerializedName("created")
    val created: String,
    @SerializedName("deletable")
    val deletable: Boolean,
    @SerializedName("message")
    val message: String,
    @SerializedName("modifiable")
    val modifiable: Boolean,
    @SerializedName("orderId")
    val orderId: String,
    @SerializedName("orderbook")
    val orderbook: Orderbook,
    @SerializedName("orderbookId")
    val orderbookId: String,
    @SerializedName("originalVolume")
    val originalVolume: Int,
    @SerializedName("price")
    val price: Double,
    @SerializedName("side")
    val side: String,
    @SerializedName("state")
    val state: String,
    @SerializedName("stateMessage")
    val stateMessage: String,
    @SerializedName("stateText")
    val stateText: String,
    @SerializedName("validUntil")
    val validUntil: String,
    @SerializedName("volume")
    val volume: Int
)

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

data class Account(
    @SerializedName("accountId")
    val accountId: String,
    @SerializedName("name")
    val name: Name,
    @SerializedName("type")
    val type: Type,
    @SerializedName("urlParameterId")
    val urlParameterId: String
)

data class Name(
    @SerializedName("value")
    val value: String
)

data class Type(
    @SerializedName("accountType")
    val accountType: String
)