package com.github.widarlein.kavanza.model

inline class TransactionType(val transactionType: String)
inline class AccountId(val value: String)

data class TransactionOptions(

    /**
     * From date on the form yyyy-MM-dd
     */
    val from: String?,

    /**
     * To date on the form yyyy-MM-dd
     */
    val to: String?,

    /**
     * Fetch transactions with maximum this value
     */
    val maxAmount: Double?,

    /**
     * Fetch transactions with minimum this value
     */
    val minAmount: Double?,

    /**
     * Only fetch transactions belonging to this orderbook id
     * Can also specify multiple ids in a comma separated list
     */
    val orderBookId: String?
) {
    internal fun toQueryMap(): Map<String, String> {
        val map = mutableMapOf<String, String>()
        from?.let {
            map["from"] = it
        }

        to?.let {
            map["to"] = it
        }

        maxAmount?.let {
            map["maxAmount"] = it.toString()
        }

        minAmount?.let {
            map["minAmount"] = minAmount.toString()
        }

        return map
    }
}

data class Transactions (

    val transactions : List<Transaction>,
    val totalNumberOfTransactions : Int
)

data class Transaction (

    val account : TransactionAccount,
    val sum : Double,
    val id : String,
    val currency : String,
    val description : String,
    val orderbook : TransactionOrderbook,
    val currencyRate : Double,
    val price : Double,
    val volume : Double,
    val amount : Double,
    val commission : Double,
    val noteId : String,
    val transactionType : String,
    val verificationDate : String
)

data class TransactionAccount (

    val type : String,
    val name : String,
    val id : String
)

data class TransactionOrderbook (

    val isin : String,
    val name : String,
    val currency : String,
    val id : String,
    val type : String,
    val flagCode : String
)

