package org.texchtown.cgv.data.naverstealshot

data class naverStealShotResponse(
    val display: Int,
    val items: List<ItemX>,
    val lastBuildDate: String,
    val start: Int,
    val total: Int
)