package com.leapsoftware.adapterdelegatecards.ui

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.widget.TextViewCompat
import androidx.recyclerview.widget.RecyclerView
import com.leapsoftware.adapterdelegatecards.R

class CardViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val image: ImageView = itemView.findViewById(R.id.card_image)
    val overlineTextView: TextView? = itemView.findViewById(R.id.card_overline)
    val titleTextView: TextView = itemView.findViewById(R.id.card_title)
    val bodyTextView: TextView? = itemView.findViewById(R.id.card_body)

    fun resetDefaults() {
        image.visibility = View.VISIBLE
        overlineTextView?.visibility = View.VISIBLE
        titleTextView.visibility = View.VISIBLE
        bodyTextView?.visibility = View.VISIBLE
    }

    fun setTitleTextAppearance(titleTextView: TextView, textStyleKey: String) {
        when (textStyleKey) {
            "h1" -> TextViewCompat.setTextAppearance(titleTextView, R.style.H1)
            "h2" -> TextViewCompat.setTextAppearance(titleTextView, R.style.H2)
            "h2-inverse" -> TextViewCompat.setTextAppearance(titleTextView, R.style.H2_Inverse)
            "h3" -> TextViewCompat.setTextAppearance(titleTextView, R.style.H3)
        }
    }
}