package com.github.widarlein.kavanza.model.getordersanddeals


import com.google.gson.annotations.SerializedName

data class Type(
    @SerializedName("accountType")
    val accountType: String
)