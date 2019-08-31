package com.leapsoftware.adapterdelegatecards.ui.delegate

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.leapsoftware.adapterdelegatecards.R
import com.leapsoftware.adapterdelegatecards.networking.FakeRequestManager
import com.leapsoftware.adapterdelegatecards.ui.FeedViewModelFactory

class DelegateFragment : Fragment() {

    private lateinit var delegateViewModel: DelegateViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val requestManager = FakeRequestManager.getInstance(context!!)
        val factory = FeedViewModelFactory(requestManager)
        delegateViewModel = ViewModelProviders.of(this, factory).get(DelegateViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        delegateViewModel =
            ViewModelProviders.of(this).get(DelegateViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_delegate, container, false)
        val textView: TextView = root.findViewById(R.id.text_delegate)
        delegateViewModel.liveDataFeedItems.observe(this, Observer {
            textView.text = it[0].title
        })
        return root
    }
}