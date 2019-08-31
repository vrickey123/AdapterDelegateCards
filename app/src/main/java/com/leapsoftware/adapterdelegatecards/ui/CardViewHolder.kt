package com.leapsoftware.adapterdelegatecards.ui

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.leapsoftware.adapterdelegatecards.R

class CardViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val image: ImageView = itemView.findViewById(R.id.card_image)
    val overlineTextView: TextView = itemView.findViewById(R.id.card_overline)
    val titleTextView: TextView = itemView.findViewById(R.id.card_title)
    val bodyTextView: TextView = itemView.findViewById(R.id.card_image)
}