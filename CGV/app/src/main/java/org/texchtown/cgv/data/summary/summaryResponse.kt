package org.texchtown.cgv.data.summary

data class summaryResponse(
    val display: Int,
    val items: List<Item>,
    val lastBuildDate: String,
    val start: Int,
    val total: Int
)