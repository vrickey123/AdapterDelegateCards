package com.leapsoftware.adapterdelegatecards.ui.delegate

import com.hannesdorfmann.adapterdelegates4.ListDelegationAdapter
import com.leapsoftware.adapterdelegatecards.data.FeedItem
import com.leapsoftware.adapterdelegatecards.ui.delegate.adapterdelegates.MaterialCardAdapterDelegate
import com.leapsoftware.adapterdelegatecards.ui.delegate.adapterdelegates.ThumbnailCardAdapterDelegate
import com.leapsoftware.adapterdelegatecards.ui.delegate.adapterdelegates.VisualCardAdapterDelegate

class FeedAdapterDelegatesManager(feedItems: List<FeedItem>) : ListDelegationAdapter<List<FeedItem>>() {

    companion object {
        const val VIEW_TYPE_MATERIAL_CARD = 1
        const val VIEW_TYPE_THUMBNAIL_CARD = 2
        const val VIEW_TYPE_VISUAL_CARD = 3
    }

    init {
        delegatesManager.addDelegate(VIEW_TYPE_MATERIAL_CARD,
            MaterialCardAdapterDelegate()
        )
        delegatesManager.addDelegate(VIEW_TYPE_THUMBNAIL_CARD,
            ThumbnailCardAdapterDelegate()
        )
        delegatesManager.addDelegate(VIEW_TYPE_VISUAL_CARD,
            VisualCardAdapterDelegate()
        )
        delegatesManager.fallbackDelegate =
            MaterialCardAdapterDelegate()
    }
}