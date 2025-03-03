package com.github.widarlein.kavanza.model


import com.google.gson.annotations.SerializedName

data class ExchangeRate(
    @SerializedName("rate")
    val rate: Rate
)

data class CurrencyPair(
    @SerializedName("baseCurrency")
    val baseCurrency: String,
    @SerializedName("quoteCurrency")
    val quoteCurrency: String
)

data class Rate(
    @SerializedName("currencyPair")
    val currencyPair: CurrencyPair,
    @SerializedName("value")
    val value: String
)