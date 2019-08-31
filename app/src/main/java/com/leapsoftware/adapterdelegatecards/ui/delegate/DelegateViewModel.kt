package com.leapsoftware.adapterdelegatecards.ui.delegate

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class DelegateViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is the adapter delegate Fragment"
    }
    val text: LiveData<String> = _text
}