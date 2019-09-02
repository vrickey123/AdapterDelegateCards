package com.leapsoftware.adapterdelegatecards.ui.standard

import androidx.recyclerview.widget.DiffUtil
import com.leapsoftware.adapterdelegatecards.data.FeedItem

class FeedItemDiffCallback : DiffUtil.ItemCallback<FeedItem>() {
    override fun areItemsTheSame(oldItem: FeedItem, newItem: FeedItem): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: FeedItem, newItem: FeedItem): Boolean {
        return oldItem.id == newItem.id
    }
}