package org.texchtown.cgv.data.navermovie

data class naverMovieSearchResponse(
    val display: Int,
    val items: List<Item>,
    val lastBuildDate: String,
    val start: Int,
    val total: Int
)