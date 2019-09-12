package com.github.widarlein.kavanza.model

data class PositionModel (
    val instrumentPositions : List<InstrumentPositions>,
    val totalBalance : Double,
    val totalProfitPercent : Double,
    val totalBuyingPower : Double,
    val totalOwnCapital : Double,
    val totalProfit : Double
)

data class InstrumentPositions (
    val instrumentType : String,
    val positions : List<Positions>,
    val totalValue : Double,
    val totalProfitValue : Double,
    val totalProfitPercent : Double,
    val todaysProfitPercent : Double
)

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