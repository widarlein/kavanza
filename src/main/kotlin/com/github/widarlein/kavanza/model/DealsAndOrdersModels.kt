package com.github.widarlein.kavanza.model

data class DealsAndOrders (
    val orders : List<Order>,
    val deals : List<String>,
    val accounts : List<DealAccount>,
    val reservedAmount : Int
)

data class Order (
    val transactionFees : TransactionFees,
    val orderbook : Orderbook,
    val account : OrderAccount,
    val status : String,
    val statusDescription : String,
    val rawStatus : String,
    val visibleOnAccountDate : String,
    val type : String,
    val sum : Int,
    val orderId : Int,
    val price : Int,
    val modifyAllowed : Boolean,
    val deletable : Boolean,
    val orderDateTime : String
)

data class DealAccount (
    val type : String,
    val name : Int,
    val id : Int
)

data class TransactionFees (
    val commission : Int,
    val marketFees : Int,
    val fundOrderFee : FundOrderFee,
    val totalFees : Int,
    val totalSum : Int,
    val totalSumWithoutFees : Int
)

data class Orderbook (
    val name : String,
    val id : String,
    val type : String,
    val currency : String,
    val marketPlace : String
)

data class FundOrderFee (
    val rate : Int,
    val sum : Int
)

data class OrderAccount (
    val type : String,
    val name : Int,
    val id : Int
)