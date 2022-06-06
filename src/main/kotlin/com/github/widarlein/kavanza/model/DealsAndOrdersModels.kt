package com.github.widarlein.kavanza.model

data class DealsAndOrders (
    val orders : List<Order>,
    val deals : List<String>,
    val accounts : List<DealAccount>,
    val reservedAmount : Double
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
    val sum : Double,
    val orderId : String,
    val price : Double,
    val modifyAllowed : Boolean,
    val deletable : Boolean,
    val orderDateTime : String
)

data class DealAccount (
    val type : String,
    val name : String,
    val id : String
)

data class TransactionFees (
    val commission : Double,
    val marketFees : Double,
    val fundOrderFee : FundOrderFee,
    val totalFees : Double,
    val totalSum : Double,
    val totalSumWithoutFees : Double
)

data class Orderbook (
    val name : String,
    val id : String,
    val type : String,
    val currency : String,
    val marketPlace : String
)

data class FundOrderFee (
    val rate : Double,
    val sum : Double
)

data class OrderAccount (
    val type : String,
    val name : String,
    val id : String
)