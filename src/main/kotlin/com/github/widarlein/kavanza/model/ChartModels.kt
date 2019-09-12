package com.github.widarlein.kavanza.model

enum class Period {
    TODAY,
    ONE_MONTH,
    THREE_MONTHS,
    ONE_WEEK,
    THIS_YEAR,
    ONE_YEAR,
    FIVE_YEARS;

    override fun toString() = super.toString().toLowerCase()
}

//TODO check suspicios floor and ceiling, but it's probably right
data class ChartData (
    val dataSeries : List<ChartDataPoint>,
    val comparisonSeries : List<ChartDataPoint>,
    val min : Double,
    val max : Double,
    val floor : Int,
    val ceiling : Int,
    val change : Double,
    val changePercent : Double,
    val comparisonName : String
)

data class ChartDataPoint (
    //TODO deserialize the timestamp to date?
    /**
     * Timestamp in the same format as 2019-08-26T00:00:00.000+0200
     */
    val timestamp : String,
    val value : Double
)