package com.example.jobis.presentation.community.search

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.jobis.R

class PostSearchFragment : Fragment() {

    companion object {
        fun newInstance() = PostSearchFragment()
    }

    private lateinit var viewModel: PostSearchViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.post_search_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(PostSearchViewModel::class.java)
        // TODO: Use the ViewModel
    }

}