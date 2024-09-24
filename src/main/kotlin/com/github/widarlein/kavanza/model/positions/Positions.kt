package com.github.widarlein.kavanza.model.positions


import com.google.gson.annotations.SerializedName

data class Positions(
    @SerializedName("cashPositions")
    val cashPositions: List<CashPosition>,
    @SerializedName("withCreditAccount")
    val withCreditAccount: Boolean,
    @SerializedName("withOrderbook")
    val withOrderbook: List<PositionData>,
    @SerializedName("withoutOrderbook")
    val withoutOrderbook: List<PositionData>
)