package com.leapsoftware.adapterdelegatecards.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.leapsoftware.adapterdelegatecards.networking.FakeRequestManager
import com.leapsoftware.adapterdelegatecards.ui.delegate.DelegateViewModel
import com.leapsoftware.adapterdelegatecards.ui.standard.StandardViewModel

class FeedViewModelFactory(
    private val fakeRequestManager: FakeRequestManager
) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        when (modelClass) {
            DelegateViewModel::class.java -> return DelegateViewModel(fakeRequestManager) as T
            StandardViewModel::class.java -> return StandardViewModel(fakeRequestManager) as T
        }
        return EmptyViewModel() as T
    }

    class EmptyViewModel() : ViewModel() {}
}