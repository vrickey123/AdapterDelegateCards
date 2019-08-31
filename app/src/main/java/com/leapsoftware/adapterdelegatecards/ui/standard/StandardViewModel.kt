package com.leapsoftware.adapterdelegatecards.ui.standard

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.leapsoftware.adapterdelegatecards.data.FeedItem
import com.leapsoftware.adapterdelegatecards.networking.FakeRequestManager

class StandardViewModel internal constructor(private val fakeRequestManager: FakeRequestManager) :
    ViewModel() {

    val liveDataFeedItems: LiveData<List<FeedItem>> = MutableLiveData<List<FeedItem>>()
        .apply {
            this.value = fakeRequestManager.getFeedItems()
        }
}