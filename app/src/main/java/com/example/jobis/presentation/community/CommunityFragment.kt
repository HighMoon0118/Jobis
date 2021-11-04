package com.example.jobis.presentation.community

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.jobis.databinding.FragmentCommunityBinding
import com.example.jobis.presentation.MainActivity
import com.example.jobis.presentation.community.popular.PopularPostFragment
import com.example.jobis.presentation.community.recent.RecentPostFragment
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class CommunityFragment: Fragment() {
    private var mainActivity: MainActivity? = null
    private lateinit var communityViewModel: CommunityViewModel
    private var _binding: FragmentCommunityBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCommunityBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        communityViewModel = ViewModelProvider(this, CommunityViewModelFactory())
//            .get(CommunityViewModel::class.java)
//        communityViewModel.postList.observe(viewLifecycleOwner,
//        Observer { postList ->
//            postList ?: return@Observer
//            updatePost(postList)
//        })
//        communityViewModel.recentPostList.observe(viewLifecycleOwner,
//            Observer { recentPostList ->
//                recentPostList ?: return@Observer
//                updateRecentPost(recentPostList)
//            })
//        communityViewModel.popularPostList.observe(viewLifecycleOwner,
//            Observer { popularPostList ->
//                popularPostList ?: return@Observer
//                updatePopularPost(popularPostList)
//            })

        val fragmentList = listOf(PopularPostFragment(), RecentPostFragment())
        val adapter = CommunityFragmentAdapter(this)
        adapter.fragmentList = fragmentList
        binding.postViewPager.adapter = adapter

        val tabTitles = listOf<String>("인기글", "최신글")
        TabLayoutMediator(binding.postTabLayout, binding.postViewPager) { tab, position ->
            tab.text = tabTitles[position]
        }.attach()

        binding.postCreateButton.setOnClickListener {
            mainActivity?.goPostCreateFragment()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is MainActivity) mainActivity = context
    }

    private fun updatePost(popularPostList: PostList, recentPostList: PostList) {
        // 리사이클러뷰로 화면에 뿌리기
        // 인기글 : 전체를 좋아요순으로 정렬 일단은
        // 최신글 : 그냥 전체에서 날짜순으로 정렬
        val postList1 = PostList()
        val postList2 = PostList()
        val postList = listOf(postList1, postList2)
        val pagerAdapter = CommunityPagerAdapter()
        pagerAdapter.postList = postList
        binding.postViewPager.adapter = pagerAdapter

        val tabTitles = listOf<String>("인기글", "최신글")
        TabLayoutMediator(binding.postTabLayout, binding.postViewPager) { tab, position ->
            tab.text = tabTitles[position]

        }.attach()
        binding.loading2.visibility = View.GONE
    }
}
