package com.github.widarlein.kavanza.model.positions


import com.google.gson.annotations.SerializedName

data class LastDeal(
    @SerializedName("date")
    val date: String,
    @SerializedName("time")
    val time: String?
)