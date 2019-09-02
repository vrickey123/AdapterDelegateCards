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

    // material, light, visual, traditional, header
    fun setCompositeTextAppearance(bodyTextView: TextView?, titleTextView: TextView, textStyleKey: String) {
        when (textStyleKey) {
            "material" -> {
                TextViewCompat.setTextAppearance(titleTextView, R.style.H5)
                bodyTextView?.let { TextViewCompat.setTextAppearance(it, R.style.Body) }
            }
            "light" -> {
                TextViewCompat.setTextAppearance(titleTextView, R.style.H5_Serif_Light)
                bodyTextView?.let { TextViewCompat.setTextAppearance(it, R.style.Body_Sans) }
            }
            "visual" -> {
                TextViewCompat.setTextAppearance(titleTextView, R.style.H6_Sans_Bold_Inverse)
                bodyTextView?.let { TextViewCompat.setTextAppearance(it, R.style.Body_Sans) }
            }
            "typeset" -> {
                TextViewCompat.setTextAppearance(titleTextView, R.style.H5_Serif)
                bodyTextView?.let { TextViewCompat.setTextAppearance(it, R.style.Body_Serif) }
            }
            "header" -> {
                TextViewCompat.setTextAppearance(titleTextView, R.style.H5_Sans_Bold)
                bodyTextView?.let { TextViewCompat.setTextAppearance(it, R.style.Body_Sans) }
            }
            "italic" -> {
                TextViewCompat.setTextAppearance(titleTextView, R.style.H5_SerifItalic)
                bodyTextView?.let { TextViewCompat.setTextAppearance(it, R.style.Body_Serif) }
            }
            else -> {
                TextViewCompat.setTextAppearance(titleTextView, R.style.H5)
                bodyTextView?.let { TextViewCompat.setTextAppearance(it, R.style.Body) }
            }
        }
    }
}