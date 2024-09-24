package com.github.widarlein.kavanza.model.positions


import com.github.widarlein.kavanza.model.ValueData
import com.google.gson.annotations.SerializedName

data class CashPosition(
    @SerializedName("account")
    val account: Account,
    @SerializedName("id")
    val id: String,
    @SerializedName("totalBalance")
    val totalBalance: ValueData
)