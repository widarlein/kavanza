package com.github.widarlein.kavanza.model


import com.google.gson.annotations.SerializedName

data class CategorizedAccountsOverview(
    @SerializedName("accounts")
    val accounts: List<CategorizedAccount>,
    @SerializedName("accountsSummary")
    val accountsSummary: AccountsSummary,
    @SerializedName("categories")
    val categories: List<Category>,
    @SerializedName("loans")
    val loans: List<Any?>
)

data class CategorizedAccount(
    @SerializedName("balance")
    val balance: ValueData,
    @SerializedName("buyingPower")
    val buyingPower: ValueData,
    @SerializedName("buyingPowerWithoutCredit")
    val buyingPowerWithoutCredit: ValueData,
    @SerializedName("categoryId")
    val categoryId: String,
    @SerializedName("credit")
    val credit: ValueData?,
    @SerializedName("currencyBalances")
    val currencyBalances: List<ValueData>,
    @SerializedName("depositInterestRate")
    val depositInterestRate: ValueData?,
    @SerializedName("errorStatus")
    val errorStatus: String,
    @SerializedName("externalAccountNumber")
    val externalAccountNumber: String?,
    @SerializedName("id")
    val id: String,
    @SerializedName("loanInterestRate")
    val loanInterestRate: ValueData?,
    @SerializedName("name")
    val name: Name,
    @SerializedName("overdrawn")
    val overdrawn: List<Overdrawn>,
    @SerializedName("overmortgaged")
    val overmortgaged: Any?,
    @SerializedName("owner")
    val owner: Boolean,
    @SerializedName("performance")
    val performance: Performance,
    @SerializedName("profit")
    val profit: Profit,
    @SerializedName("settings")
    val settings: Settings,
    @SerializedName("status")
    val status: String,
    @SerializedName("totalAcquiredValue")
    val totalAcquiredValue: ValueData,
    @SerializedName("totalValue")
    val totalValue: ValueData,
    @SerializedName("type")
    val type: String,
    @SerializedName("urlParameterId")
    val urlParameterId: String
)

data class AccountsSummary(
    @SerializedName("buyingPower")
    val buyingPower: ValueData,
    @SerializedName("performance")
    val performance: Performance,
    @SerializedName("totalValue")
    val totalValue: ValueData
)

data class Category(
    @SerializedName("buyingPower")
    val buyingPower: ValueData,
    @SerializedName("id")
    val id: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("performance")
    val performance: Performance?,
    @SerializedName("profit")
    val profit: Profit,
    @SerializedName("savingsGoalView")
    val savingsGoalView: Any?,
    @SerializedName("sortOrder")
    val sortOrder: Int,
    @SerializedName("totalValue")
    val totalValue: ValueData
)

data class Name(
    @SerializedName("defaultName")
    val defaultName: String,
    @SerializedName("userDefinedName")
    val userDefinedName: String?
)

data class Overdrawn(
    @SerializedName("amount")
    val amount: ValueData,
    @SerializedName("type")
    val type: String
)

data class Performance(
    @SerializedName("ALL_TIME")
    val allTime: PerformanceData,
    @SerializedName("ONE_MONTH")
    val onemonth: PerformanceData,
    @SerializedName("ONE_WEEK")
    val oneweek: PerformanceData,
    @SerializedName("ONE_YEAR")
    val oneyear: PerformanceData,
    @SerializedName("THIS_YEAR")
    val thisyear: PerformanceData,
    @SerializedName("THREE_MONTHS")
    val threemonths: PerformanceData,
    @SerializedName("THREE_YEARS")
    val threeyears: PerformanceData
)

data class Profit(
    @SerializedName("absolute")
    val absolute: ValueData,
    @SerializedName("relative")
    val relative: ValueData
)

data class Settings(
    @SerializedName("IS_HIDDEN")
    val isHidden: Boolean
)

data class PerformanceData(
    @SerializedName("absolute")
    val absolute: ValueData,
    @SerializedName("relative")
    val relative: ValueData
)
