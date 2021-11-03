package com.example.jobis.presentation.community.create

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.jobis.R

class PostCreateFragment : Fragment() {

    companion object {
        fun newInstance() = PostCreateFragment()
    }

    private lateinit var viewModel: PostCreateViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.post_create_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(PostCreateViewModel::class.java)
        // TODO: Use the ViewModel
    }

}