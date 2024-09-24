package com.github.widarlein.kavanza.model.positions


import com.google.gson.annotations.SerializedName

data class Account(
    @SerializedName("hasCredit")
    val hasCredit: Boolean,
    @SerializedName("id")
    val id: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("type")
    val type: String,
    @SerializedName("urlParameterId")
    val urlParameterId: String
)