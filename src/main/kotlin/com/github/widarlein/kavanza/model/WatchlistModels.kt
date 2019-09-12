package com.github.widarlein.kavanza.model

data class Watchlist (
    val orderbooks : List<String>,
    val editable : Boolean,
    val name : String,
    val id : String
)