package com.github.widarlein.kavanza.model.getordersanddeals


import com.google.gson.annotations.SerializedName

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