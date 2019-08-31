package com.leapsoftware.adapterdelegatecards.ui.standard

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
import com.leapsoftware.adapterdelegatecards.ui.delegate.DelegateViewModel

class StandardFragment : Fragment() {

    private lateinit var standardViewModel: StandardViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val requestManager = FakeRequestManager.getInstance(context!!)
        val factory = FeedViewModelFactory(requestManager)
        standardViewModel = ViewModelProviders.of(this, factory).get(StandardViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        standardViewModel =
            ViewModelProviders.of(this).get(StandardViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_standard, container, false)
        val textView: TextView = root.findViewById(R.id.text_standard)
        standardViewModel.liveDataFeedItems.observe(this, Observer {
            textView.text = it[0].title
        })
        return root
    }
}