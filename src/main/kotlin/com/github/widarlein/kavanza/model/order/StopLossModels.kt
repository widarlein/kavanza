package com.github.widarlein.kavanza.model.order


import com.github.widarlein.kavanza.model.AccountId
import com.google.gson.annotations.SerializedName

data class StopLossOperation(
    @SerializedName("accountId")
    val accountId: AccountId,
    @SerializedName("orderBookId")
    val orderBookId: String,

    /**
     * Unclear, but 0 works as default
     */
    @SerializedName("parentStopLossId")
    val parentStopLossId: String,
    @SerializedName("stopLossOrderEvent")
    val stopLossOrderEvent: StopLossOrderEvent,
    @SerializedName("stopLossTrigger")
    val stopLossTrigger: StopLossTrigger
)

data class StopLossOrderEvent(
    @SerializedName("price")
    val price: Double,
    /**
     * MONETARY works. Unclear what else there is
     */
    @SerializedName("priceType")
    val priceType: String = "MONETARY",
    @SerializedName("shortSellingAllowed")
    val shortSellingAllowed: Boolean = false,
    @SerializedName("type")
    val type: OrderSide,
    @SerializedName("validDays")
    val validDays: Int,
    @SerializedName("volume")
    val volume: Int
)

data class StopLossTrigger(
    /**
     * false works. Don't know what it means
     */
    @SerializedName("triggerOnMarketMakerQuote")
    val triggerOnMarketMakerQuote: Boolean,

    /**
     * LESS_OR_EQUAL for i.e. selling when price drops under a certain price
     * MORE_OR_EQUAL  for i.e. buying when price rises above a certain price
     */
    @SerializedName("type")
    val type: String,
    /**
     * Format: 2024-10-29
     * Avanza default is 1 month from placing the stop loss.
     */
    @SerializedName("validUntil")
    val validUntil: String,
    @SerializedName("value")
    val value: Double,
    /**
     * MONETARY works. Unclear what else there is
     */
    @SerializedName("valueType")
    val valueType: String = "MONETARY"
)

data class StopLossOperationResponse(
    @SerializedName("status")
    val status: String,
    @SerializedName("stoplossOrderId")
    val stoplossOrderId: String
)