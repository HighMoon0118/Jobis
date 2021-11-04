package com.example.jobis.presentation.community.recent

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.jobis.R
import com.example.jobis.databinding.FragmentCommunityBinding
import com.example.jobis.databinding.FragmentRecentPostBinding
import com.example.jobis.presentation.community.CommunityViewModel
import com.example.jobis.presentation.community.CustomPostAdapter
import com.example.jobis.presentation.community.PostList

class RecentPostFragment : Fragment() {

    private lateinit var recentPostViewModel: RecentPostViewModel
    private var _binding: FragmentRecentPostBinding ? = null
    private val binding get() = _binding!!
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentRecentPostBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recentPostViewModel = ViewModelProvider(this, RecentPostViewModelFactory())
            .get(RecentPostViewModel::class.java)
        recentPostViewModel.recentPostList.observe(viewLifecycleOwner,
            Observer { recentPostList ->
                recentPostList ?: return@Observer
                updateRecentPost(recentPostList)
            })
        binding.recentProgressBar.visibility = View.VISIBLE
        recentPostViewModel.loadRecentPosts()
    }

    fun updateRecentPost(recentPostList: PostList) {
        val adapter = CustomPostAdapter()
        adapter.listData = recentPostList
        binding.recentRecyclerView.adapter = adapter
        binding.recentRecyclerView.layoutManager = StaggeredGridLayoutManager(
            1,
            StaggeredGridLayoutManager.VERTICAL)
        binding.recentProgressBar.visibility = View.GONE
    }
}