package com.leapsoftware.adapterdelegatecards.ui.delegate.adapterdelegates

import android.widget.TextView
import androidx.core.widget.TextViewCompat
import androidx.recyclerview.widget.RecyclerView
import com.hannesdorfmann.adapterdelegates4.AdapterDelegate
import com.leapsoftware.adapterdelegatecards.R
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
        cardViewHolder.overlineTextView.text = items[position].overline
        cardViewHolder.titleTextView.text = items[position].title
        cardViewHolder.bodyTextView.text = items[position].body

        setTitleTextAppearance(cardViewHolder.titleTextView, items[position].textStyleKey)
    }

    private fun setTitleTextAppearance(titleTextView: TextView, textStyleKey: String) {
        when (textStyleKey) {
            "h1" -> TextViewCompat.setTextAppearance(titleTextView, R.style.H1)
            "h2" -> TextViewCompat.setTextAppearance(titleTextView, R.style.H2)
            "h3" -> TextViewCompat.setTextAppearance(titleTextView, R.style.H3)
        }
    }
}