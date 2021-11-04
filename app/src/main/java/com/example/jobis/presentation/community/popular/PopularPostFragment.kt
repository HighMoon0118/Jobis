package com.example.jobis.presentation.community.popular

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.jobis.databinding.FragmentPopularPostBinding
import com.example.jobis.presentation.community.CustomPostAdapter
import com.example.jobis.presentation.community.PostList

class PopularPostFragment : Fragment() {

    private lateinit var popularPostViewModel: PopularPostViewModel
    private var _binding: FragmentPopularPostBinding? = null
    private val binding get() = _binding!!
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentPopularPostBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        popularPostViewModel = ViewModelProvider(this, PopularPostViewModelFactory())
            .get(PopularPostViewModel::class.java)
        popularPostViewModel.popularPostList.observe(viewLifecycleOwner,
            Observer { popularPostList ->
                popularPostList ?: return@Observer
                updatePopularPost(popularPostList)
            })
        binding.popularProgressBar.visibility = View.VISIBLE
        popularPostViewModel.loadPopularPosts()
    }

    fun updatePopularPost(popularPostList: PostList) {
        val adapter = CustomPostAdapter()
        adapter.listData = popularPostList
        binding.popularRecyclerView.adapter = adapter
        binding.popularRecyclerView.layoutManager = StaggeredGridLayoutManager(
            1,
            StaggeredGridLayoutManager.VERTICAL)
        binding.popularProgressBar.visibility = View.GONE
    }

}