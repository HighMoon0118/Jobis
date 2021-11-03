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
        communityViewModel = ViewModelProvider(this, CommunityViewModelFactory())
            .get(CommunityViewModel::class.java)
        communityViewModel.postList.observe(viewLifecycleOwner,
        Observer { postList ->
            postList ?: return@Observer
            updatePost(postList)
        })

        // 처음에 한번 전체데이터 가져오기
        binding.loading2.visibility = View.VISIBLE
        communityViewModel.loadAllPosts()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is MainActivity) mainActivity = context
    }

    private fun updatePost(postList: PostList) {
        // 리사이클러뷰로 화면에 뿌리기
        Log.d("test", "라이브데이터 감지!! ${postList}")
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
