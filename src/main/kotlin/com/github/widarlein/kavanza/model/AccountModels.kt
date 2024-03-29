package com.github.widarlein.kavanza.model

data class Overview(
    val accounts: List<Account>,
    val totalOwnCapital: Double,
    val totalBuyingPower: Double,
    val numberOfOrders: Long,
    val numberOfDeals: Long,
    val numberOfTransfers: Long,
    val numberOfIntradayTransfers: Long,
    val totalPerformancePercent: Double,
    val totalBalance: Double,
    val totalPerformance: Double
)

data class Account(
    val name: String,
    val accountType: String,
    val interestRate: Double,
    val depositable: Boolean,
    val active: Boolean,
    val accountId: String,
    val accountPartlyOwned: Boolean,
    val attorney: Boolean,
    val tradable: Boolean,
    val totalBalance: Double,
    val totalBalanceDue: Double,
    val ownCapital: Double,
    val buyingPower: Double,
    val totalProfitPercent: Double,
    val performance: Double,
    val performancePercent: Double,
    val totalProfit: Double
)

data class AccountOverview(
    val courtageClass: String,
    val depositable: Boolean,
    val accountType: String,
    val withdrawable: Boolean,
    val clearingNumber: String,
    val instrumentTransferPossible: Boolean,
    val internalTransferPossible: Boolean,
    val jointlyOwned: Boolean,
    val accountId: String,
    val accountTypeName: String,
    val interestRate: Double,
    val numberOfOrders: Long,
    val numberOfDeals: Long,
    val performanceSinceOneWeek: Double,
    val performanceSinceOneMonth: Double,
    val performanceSinceThreeMonths: Double,
    val performanceSinceSixMonths: Double,
    val performanceSinceOneYear: Double,
    val performanceSinceThreeYears: Double,
    val performanceSinceOneWeekPercent: Double,
    val performanceSinceOneMonthPercent: Double,
    val performanceSinceThreeMonthsPercent: Double,
    val performanceSinceSixMonthsPercent: Double,
    val performanceSinceOneYearPercent: Double,
    val performanceSinceThreeYearsPercent: Double,
    val allowMonthlySaving: Boolean,
    val totalProfit: Double,
    val creditLimit: Double,
    val currencyAccounts: List<CurrencyAccount>,
    val forwardBalance: Double,
    val reservedAmount: Double,
    val totalCollateralValue: Double,
    val totalPositionsValue: Double,
    val buyingPower: Double,
    val totalProfitPercent: Double,
    val availableSuperLoanAmount: Double,
    val performancePercent: Double,
    val accruedInterest: Double,
    val overMortgaged: Boolean,
    val creditAfterInterest: Double,
    val overdrawn: Boolean,
    val performance: Double,
    val totalBalance: Double,
    val ownCapital: Double,
    val numberOfTransfers: Long,
    val numberOfIntradayTransfers: Long,

    /* TODO These are "temporarily" out commented because the API is returning "NaN" as a string value on certain accounts
        and I don't have the time right now to accommodate for that
    val standardDeviation: Double,
    val sharpeRatio: Double
    */
)

data class CurrencyAccount(val currency: String, val balance: Double)