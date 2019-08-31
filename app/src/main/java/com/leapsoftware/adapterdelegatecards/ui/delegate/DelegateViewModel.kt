package com.leapsoftware.adapterdelegatecards.ui.delegate

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.leapsoftware.adapterdelegatecards.data.FeedItem
import com.leapsoftware.adapterdelegatecards.networking.FakeRequestManager

class DelegateViewModel internal constructor(private val fakeRequestManager: FakeRequestManager) :
    ViewModel() {

    val liveDataFeedItems: LiveData<List<FeedItem>> = MutableLiveData<List<FeedItem>>()
        .apply {
            this.value = fakeRequestManager.getFeedItems()
        }
}