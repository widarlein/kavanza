package com.github.widarlein.kavanza.model

import com.google.gson.annotations.SerializedName

data class SearchRequest(
    @SerializedName("originPath")
    val originPath: String,
    @SerializedName("originPlatform")
    val originPlatform: String,
    @SerializedName("pagination")
    val pagination: Pagination,
    @SerializedName("query")
    val query: String,
    @SerializedName("screenSize")
    val screenSize: String,
    @SerializedName("searchFilter")
    val searchFilter: SearchFilter,
    @SerializedName("searchSessionId")
    val searchSessionId: String
)

data class SearchFilter(
    @SerializedName("types")
    val types: List<String>
)

data class Pagination(
    @SerializedName("from")
    val from: Int,
    @SerializedName("size")
    val size: Int
)

data class SearchResponse(
    @SerializedName("facets")
    val facets: Facets,
    @SerializedName("hits")
    val hits: List<Hit>,
    @SerializedName("pagination")
    val pagination: Pagination,
    @SerializedName("searchFilter")
    val searchFilter: SearchFilter,
    @SerializedName("searchQuery")
    val searchQuery: String,
    @SerializedName("totalNumberOfHits")
    val totalNumberOfHits: Int
)

data class Facets(
    @SerializedName("types")
    val types: List<Type>
)

data class Hit(
    @SerializedName("buyable")
    val buyable: Boolean,
    @SerializedName("description")
    val description: String,
    @SerializedName("flagCode")
    val flagCode: String?,
    @SerializedName("fundTags")
    val fundTags: List<FundTag>,
    @SerializedName("highlightedDescription")
    val highlightedDescription: String,
    @SerializedName("highlightedSubType")
    val highlightedSubType: String,
    @SerializedName("highlightedTitle")
    val highlightedTitle: String,
    @SerializedName("marketPlaceName")
    val marketPlaceName: String,
    @SerializedName("orderBookId")
    val orderBookId: String,
    @SerializedName("path")
    val path: Any?,
    @SerializedName("price")
    val price: Price,
    @SerializedName("sellable")
    val sellable: Boolean,
    @SerializedName("stockSectors")
    val stockSectors: List<StockSector>,
    @SerializedName("subType")
    val subType: Any?,
    @SerializedName("title")
    val title: String,
    @SerializedName("tradeable")
    val tradeable: Boolean,
    @SerializedName("type")
    val type: String,
    @SerializedName("urlSlugName")
    val urlSlugName: String
)

data class Type(
    @SerializedName("count")
    val count: Int,
    @SerializedName("type")
    val type: String
)

data class FundTag(
    @SerializedName("category")
    val category: String,
    @SerializedName("highlightedTitle")
    val highlightedTitle: Any?,
    @SerializedName("tagCategory")
    val tagCategory: String,
    @SerializedName("title")
    val title: String
)

data class Price(
    @SerializedName("currency")
    val currency: String,
    @SerializedName("last")
    val last: String,
    @SerializedName("spread")
    val spread: String?,
    @SerializedName("threeMonthsAgoChangeDirection")
    val threeMonthsAgoChangeDirection: Int,
    @SerializedName("threeMonthsAgoChangePercent")
    val threeMonthsAgoChangePercent: String?,
    @SerializedName("todayChangeDirection")
    val todayChangeDirection: Int,
    @SerializedName("todayChangePercent")
    val todayChangePercent: String,
    @SerializedName("todayChangeValue")
    val todayChangeValue: String
)

data class StockSector(
    @SerializedName("englishName")
    val englishName: String,
    @SerializedName("highlightedName")
    val highlightedName: Any?,
    @SerializedName("id")
    val id: Int,
    @SerializedName("level")
    val level: Int,
    @SerializedName("name")
    val name: String
)