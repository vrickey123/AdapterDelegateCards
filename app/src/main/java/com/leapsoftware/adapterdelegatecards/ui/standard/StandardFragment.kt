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

class StandardFragment : Fragment() {

    private lateinit var standardViewModel: StandardViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        standardViewModel =
            ViewModelProviders.of(this).get(StandardViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_standard, container, false)
        val textView: TextView = root.findViewById(R.id.text_standard)
        standardViewModel.text.observe(this, Observer {
            textView.text = it
        })
        return root
    }
}