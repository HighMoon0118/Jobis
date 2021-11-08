package com.ssafy.jobis.presentation.community.detail.ui.detail

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import com.ssafy.jobis.data.response.PostResponse
import com.ssafy.jobis.databinding.CommunityDetailFragmentBinding

class CommunityDetailFragment : Fragment() {
    private lateinit var communityDetailViewModel: CommunityDetailViewModel
    private var _binding: CommunityDetailFragmentBinding? = null
    private val binding get() = _binding!!

    companion object {
        fun newInstance() = CommunityDetailFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = CommunityDetailFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        communityDetailViewModel = ViewModelProvider(this, CommunityDetailViewModelFactory())
            .get(CommunityDetailViewModel::class.java)
        communityDetailViewModel.post.observe(viewLifecycleOwner,
            Observer { post ->
                post ?: return@Observer
                updateUi(post)
            })
        val id = arguments?.getString("id")
        communityDetailViewModel.loadPost(id!!)
    }

    private fun updateUi(post: PostResponse) {
        Log.d("test", "updateUi! : ${post}")
    }

}