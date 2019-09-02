package com.leapsoftware.adapterdelegatecards.ui.delegate.adapterdelegates

import androidx.recyclerview.widget.RecyclerView
import com.hannesdorfmann.adapterdelegates4.AdapterDelegate
import com.leapsoftware.adapterdelegatecards.data.FeedItem
import com.leapsoftware.adapterdelegatecards.ui.CardViewHolder

abstract class AbstractCardAdapterDelegate : AdapterDelegate<List<FeedItem>>() {

    override fun onBindViewHolder(
        items: List<FeedItem>,
        position: Int,
        holder: RecyclerView.ViewHolder,
        payloads: MutableList<Any>
    ) {
        val cardViewHolder = holder as CardViewHolder
        cardViewHolder.resetDefaults()
        cardViewHolder.overlineTextView?.text = items[position].overline
        cardViewHolder.titleTextView.text = items[position].title
        cardViewHolder.bodyTextView?.text = items[position].body
        cardViewHolder.setCompositeTextAppearance(cardViewHolder.bodyTextView, cardViewHolder.titleTextView, items[position].textStyleKey)
    }

}