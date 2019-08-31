package com.leapsoftware.adapterdelegatecards.data

data class FeedItem(
    val id: Int,
    val layoutKey: String,
    val textStyleKey: String,
    val title: String,
    val body: String,
    val overline: String
)