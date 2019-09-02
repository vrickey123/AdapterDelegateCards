package com.leapsoftware.adapterdelegatecards.ui.delegate.adapterdelegates

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.hannesdorfmann.adapterdelegates4.AdapterDelegate
import com.leapsoftware.adapterdelegatecards.R
import com.leapsoftware.adapterdelegatecards.data.FeedItem
import com.leapsoftware.adapterdelegatecards.ui.CardViewHolder

class VisualCardAdapterDelegate() : AbstractCardAdapterDelegate() {
    override fun onCreateViewHolder(parent: ViewGroup): RecyclerView.ViewHolder {
        return CardViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.card_visual, parent, false))
    }

    override fun isForViewType(items: List<FeedItem>, position: Int): Boolean {
        return items[position].layoutKey == "visual"
    }

    override fun onBindViewHolder(
        items: List<FeedItem>,
        position: Int,
        holder: RecyclerView.ViewHolder,
        payloads: MutableList<Any>
    ) {
        super.onBindViewHolder(items, position, holder, payloads)
    }
}