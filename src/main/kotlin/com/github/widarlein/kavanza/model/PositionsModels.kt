package com.github.widarlein.kavanza.model


// This remained after cleanup and is used in InstrumentModels.kt. Want to remove but a bit unclear if it is still relevant
data class Positions (
    val accountName : String,
    val accountType : String,
    val depositable : Boolean,
    val accountId : Int,
    val volume : Double,
    val averageAcquiredPrice : Double,
    val profitPercent : Double,
    val acquiredValue : Double,
    val profit : Double,
    val value : Double,
    val currency : String,
    val orderbookId : Int,
    val tradable : Boolean,
    val lastPrice : Double,
    val lastPriceUpdated : String,
    val change : Double,
    val changePercent : Double,
    val flagCode : String,
    val name : String
)