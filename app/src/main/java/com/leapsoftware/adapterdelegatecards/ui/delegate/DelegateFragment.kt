package com.leapsoftware.adapterdelegatecards.ui.delegate

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
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
        return inflater.inflate(R.layout.fragment_list_view, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val recyclerView = view.findViewById<RecyclerView>(R.id.fragment_recycler_view)

        val linearLayoutManager = LinearLayoutManager(context)
        val adapterDelegatesManager = FeedAdapterDelegatesManager()

        recyclerView.layoutManager = linearLayoutManager
        recyclerView.adapter = adapterDelegatesManager
        recyclerView.addItemDecoration(DividerItemDecoration(recyclerView.context, linearLayoutManager.orientation))

        subscribeToUi(adapterDelegatesManager)
    }

    private fun subscribeToUi(adapterDelegatesManager: FeedAdapterDelegatesManager) {
        delegateViewModel.liveDataFeedItems.observe(viewLifecycleOwner, Observer { feedItems ->
            adapterDelegatesManager.items = feedItems
            adapterDelegatesManager.notifyDataSetChanged()
        })
    }
}