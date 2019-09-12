package com.github.widarlein.kavanza.model

data class OrderbookListItem (
    val priceThreeMonthsAgo : Double,
    val currency : String,
    val highestPrice : Double,
    val lowestPrice : Double,
    val lastPrice : Double,
    val change : Double,
    val changePercent : Double,
    val updated : String,
    val totalVolumeTraded : Int,
    val flagCode : String,
    val tradable : Boolean,
    val instrumentType : String,
    val name : String,
    val id : String
)