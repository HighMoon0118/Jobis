package com.ssafy.jobis.presentation.study

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.ssafy.jobis.databinding.ActivitySearchStudyBinding
import com.ssafy.jobis.presentation.study.adapter.SearchResultAdapter


class SearchStudy : AppCompatActivity() {

    private var mBinding: ActivitySearchStudyBinding? = null
    private val binding get() = mBinding!!




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mBinding = ActivitySearchStudyBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val searchResult = arrayListOf(
            SearchResult("대전 알고리즘 스터디", "월, 목 저녁 8시\n알고리즘 스터디원 모집합니다\n #백준 골드 이상", "2021.10.20", "4/5"),
            SearchResult("대전 알고리즘 스터디", "월, 목 저녁 8시\n알고리즘 스터디원 모집합니다\n #백준 골드 이상", "2021.10.20", "4/5"),
            SearchResult("대전 알고리즘 스터디", "월, 목 저녁 8시\n알고리즘 스터디원 모집합니다\n #백준 골드 이상", "2021.10.20", "4/5"),
            SearchResult("대전 알고리즘 스터디", "월, 목 저녁 8시\n알고리즘 스터디원 모집합니다\n #백준 골드 이상", "2021.10.20", "4/5"),
            SearchResult("대전 알고리즘 스터디", "월, 목 저녁 8시\n알고리즘 스터디원 모집합니다\n #백준 골드 이상", "2021.10.20", "4/5")
        )



        binding.rvSearchResult.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        binding.rvSearchResult.setHasFixedSize(true)
        binding.rvSearchResult.adapter = SearchResultAdapter(searchResult)

    }



}