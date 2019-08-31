package com.leapsoftware.adapterdelegatecards.ui.delegate

import android.app.Activity
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.hannesdorfmann.adapterdelegates4.AdapterDelegate
import com.leapsoftware.adapterdelegatecards.R
import com.leapsoftware.adapterdelegatecards.data.FeedItem
import com.leapsoftware.adapterdelegatecards.ui.CardViewHolder

class MaterialCardAdapterDelegate() : AdapterDelegate<List<FeedItem>>() {

    override fun onCreateViewHolder(parent: ViewGroup): RecyclerView.ViewHolder {
        return CardViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.card_material, parent, false))
    }

    override fun isForViewType(items: List<FeedItem>, position: Int): Boolean {
        return items[position].layoutKey == "material"
    }

    override fun onBindViewHolder(
        items: List<FeedItem>,
        position: Int,
        holder: RecyclerView.ViewHolder,
        payloads: MutableList<Any>
    ) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}