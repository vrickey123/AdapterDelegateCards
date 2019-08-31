package com.leapsoftware.adapterdelegatecards.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class FeedItem(
    val id: Int,
    val layoutKey: String,
    val textStyleKey: String,
    val title: String,
    val body: String,
    val overline: String
) : Parcelable