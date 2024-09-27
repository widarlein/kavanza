package com.github.widarlein.kavanza.model.getordersanddeals


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
    /**
     * ACTIVE, FAILED
     */
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