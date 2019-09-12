package com.github.widarlein.kavanza.model

data class SearchHits(
    val totalNumberOfHits: Int,
    val hits: List<Hits>
)

data class Hits (
    val instrumentType: String,
    val numberOfHits: Int,
    val topHits: List<TopHits>
)

data class TopHits (
    val currency: String,
    val lastPrice: Double,
    val changePercent: Double,
    val name: String,
    val id: String,
    val tradable: Boolean,
    val tickerSymbol: String,
    val flagCode: String
)