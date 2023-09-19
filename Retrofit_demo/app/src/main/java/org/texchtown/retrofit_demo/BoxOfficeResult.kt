package org.texchtown.retrofit_demo

data class BoxOfficeResult(
    val boxofficeType: String,
    val showRange: String,
    val weeklyBoxOfficeList: List<WeeklyBoxOffice>,
    val yearWeekTime: String
)