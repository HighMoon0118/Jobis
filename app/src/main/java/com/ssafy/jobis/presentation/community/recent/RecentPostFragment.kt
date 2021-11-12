package com.ssafy.jobis.presentation.community.recent

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.ssafy.jobis.R
import com.ssafy.jobis.data.response.PostResponse
import com.ssafy.jobis.data.response.PostResponseList
import com.ssafy.jobis.databinding.FragmentRecentPostBinding
import com.ssafy.jobis.presentation.MainActivity
import com.ssafy.jobis.presentation.community.CustomPostAdapter

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

        setUpSpinner()
        recentPostViewModel.loadRecentPosts()
    }

    fun updateRecentPost(recentPostList: PostResponseList) {
        val adapter = CustomPostAdapter()
        adapter.listData = recentPostList
        adapter.setOnItemClickListener(object: CustomPostAdapter.OnItemClickListener{
            override fun onItemClick(v: View, post: PostResponse, pos: Int) {
                if (post.id != null) {
                    (activity as MainActivity).goCommunityDetailActivity(post.id)
                }
            }
        })
        binding.recentRecyclerView.adapter = adapter
        binding.recentRecyclerView.layoutManager = StaggeredGridLayoutManager(
            1,
            StaggeredGridLayoutManager.VERTICAL)
        binding.recentProgressBar.visibility = View.GONE
        binding.recentPostCountTextView.text = "총 ${recentPostList.size.toString()}개 "
    }

    fun setUpSpinner() {
        val categories = resources.getStringArray(R.array.post_category)
        val adapter = ArrayAdapter((activity as MainActivity), R.layout.support_simple_spinner_dropdown_item, categories)
        binding.recentPostSpinner.adapter = adapter
        binding.recentPostSpinner.onItemSelectedListener = object: AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                recentPostViewModel.filterPost(p2)
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
            }
        }
    }

}