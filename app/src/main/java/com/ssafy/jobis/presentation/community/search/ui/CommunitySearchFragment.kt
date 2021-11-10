package com.ssafy.jobis.presentation.community.search.ui

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ssafy.jobis.R

class CommunitySearchFragment : Fragment() {

    companion object {
        fun newInstance() = CommunitySearchFragment()
    }

    private lateinit var viewModel: CommunitySearchViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.community_search_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(CommunitySearchViewModel::class.java)
        // TODO: Use the ViewModel
    }

}