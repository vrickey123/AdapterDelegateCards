package com.leapsoftware.adapterdelegatecards.ui.standard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.leapsoftware.adapterdelegatecards.R
import com.leapsoftware.adapterdelegatecards.networking.FakeRequestManager
import com.leapsoftware.adapterdelegatecards.ui.FeedViewModelFactory

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
        return inflater.inflate(R.layout.fragment_list_view, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val feedAdapter = FeedAdapter()
        val recyclerView = view.findViewById<RecyclerView>(R.id.fragment_recycler_view)
        recyclerView.adapter = feedAdapter
        recyclerView.layoutManager = LinearLayoutManager(context)

        subscribeToUi(feedAdapter)

    }

    fun subscribeToUi(feedAdapter: FeedAdapter) {
        standardViewModel.liveDataFeedItems.observe(viewLifecycleOwner, Observer { feedItems ->
            feedAdapter.submitList(feedItems)
        })
    }
}