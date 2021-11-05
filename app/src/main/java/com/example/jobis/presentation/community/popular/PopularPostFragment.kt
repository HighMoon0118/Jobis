package com.example.jobis.presentation.community.popular

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.setFragmentResult
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.jobis.data.model.Post
import com.example.jobis.data.repository.CommunityRepository
import com.example.jobis.data.response.PostResponse
import com.example.jobis.data.response.PostResponseList
import com.example.jobis.databinding.FragmentPopularPostBinding
import com.example.jobis.presentation.MainActivity
import com.example.jobis.presentation.community.CommunityFragment
import com.example.jobis.presentation.community.CustomPostAdapter

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
//    fun aaa(id: String) {
//        val bundle = bundleOf("id" to id)
//        (parentFragment as CommunityFragment).setFragmentResult("request", bundle)
//    }
    fun updatePopularPost(popularPostList: PostResponseList) {
        val adapter = CustomPostAdapter()
        adapter.listData = popularPostList
        adapter.setOnItemClickListener(object: CustomPostAdapter.OnItemClickListener{
            override fun onItemClick(v: View, post: PostResponse, pos: Int) {
                if (post.id != null) {
                    (activity as MainActivity).goCommunityDetailActivity(post.id)
                }
            }
        })

        binding.popularRecyclerView.adapter = adapter
        binding.popularRecyclerView.layoutManager = StaggeredGridLayoutManager(
            1,
            StaggeredGridLayoutManager.VERTICAL)
        binding.popularProgressBar.visibility = View.GONE
    }

}