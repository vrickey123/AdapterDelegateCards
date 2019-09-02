package com.leapsoftware.adapterdelegatecards.ui.standard

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.leapsoftware.adapterdelegatecards.R
import com.leapsoftware.adapterdelegatecards.data.FeedItem
import com.leapsoftware.adapterdelegatecards.ui.CardViewHolder

class FeedAdapter : ListAdapter<FeedItem, CardViewHolder>(FeedItemDiffCallback()) {

    companion object {
        const val VIEW_TYPE_MATERIAL_CARD = 1
        const val VIEW_TYPE_THUMBNAIL_CARD = 2
        const val VIEW_TYPE_VISUAL_CARD = 3
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            VIEW_TYPE_MATERIAL_CARD -> CardViewHolder(inflater.inflate(R.layout.card_material, parent, false))
            VIEW_TYPE_THUMBNAIL_CARD -> CardViewHolder(inflater.inflate(R.layout.card_thumbnail, parent, false))
            VIEW_TYPE_VISUAL_CARD -> CardViewHolder(inflater.inflate(R.layout.card_visual, parent, false))
            else -> CardViewHolder(inflater.inflate(R.layout.card_material, parent, false))
        }
    }

    override fun onBindViewHolder(holder: CardViewHolder, position: Int) {
        holder.resetDefaults()
        holder.overlineTextView?.text = getItem(position).overline
        holder.titleTextView.text = getItem(position).title
        holder.bodyTextView?.text = getItem(position).body
        holder.setCompositeTextAppearance(holder.bodyTextView, holder.titleTextView, getItem(position).textStyleKey)
    }

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position).layoutKey) {
            "material" -> VIEW_TYPE_MATERIAL_CARD
            "thumbnail" -> VIEW_TYPE_THUMBNAIL_CARD
            "visual" -> VIEW_TYPE_VISUAL_CARD
            else -> VIEW_TYPE_MATERIAL_CARD
        }
    }
}