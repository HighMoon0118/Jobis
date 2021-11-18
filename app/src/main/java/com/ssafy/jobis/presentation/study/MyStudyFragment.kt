package com.ssafy.jobis.presentation.study

import android.animation.ObjectAnimator
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.ssafy.jobis.R
import com.ssafy.jobis.databinding.FragmentMyStudyBinding
import com.ssafy.jobis.presentation.study.adapter.MyStudyAdapter

class MyStudyFragment(val myContext: Context): Fragment() {

    private var _binding: FragmentMyStudyBinding? = null
    private val binding get() = _binding!!

    private var isFabOpen = false

    private lateinit var viewModel: StudyViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,savedInstanceState: Bundle?): View? {

        _binding = FragmentMyStudyBinding.inflate(inflater, container, false)

        activity?.run {
            viewModel = ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory(this.application)).get(StudyViewModel::class.java)
        }

        viewModel.studyList.observe(viewLifecycleOwner,  {
// for문을 통해 스터디를 다 돌면서 파이어스토어 스케쥴이 있으면 가져옴
// 거기서 오늘과 가장 가까운 스케쥴의 dday를 계산해서 새로운 리스트에 넣음, 만약 스케쥴이 없다면 -1을 넣음
//            val list = List<Int>(it.size){0}

            val studyAdapter = MyStudyAdapter(myContext, it)
            binding.rvMyStudy.adapter = studyAdapter
        })

//        binding.fabMyStudy.setOnClickListener {
//            val intent = Intent(context, CreateStudy::class.java)
//            startActivity(intent)
//        }

        binding.fabMyStudy.setOnClickListener {
            toggleFab()
        }

        binding.fabCreateStudy.setOnClickListener {
            Toast.makeText(activity, "생성", Toast.LENGTH_SHORT).show()
            val intent = Intent(context, CreateStudy::class.java)
            startActivity(intent)
        }
        
        binding.fabSearchStudy.setOnClickListener {
            Toast.makeText(activity, "검색", Toast.LENGTH_SHORT).show()
            val intent = Intent(context, SearchStudy::class.java)
            startActivity(intent)
        }



        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }


    private fun toggleFab() {

        if (isFabOpen) {
            ObjectAnimator.ofFloat(binding.fabSearchStudy, "translationY", 0f).apply{start()}
            ObjectAnimator.ofFloat(binding.fabCreateStudy, "translationY", 0f).apply{start()}
            binding.fabMyStudy.setImageResource(R.drawable.ic_baseline_check_24_white)
        } else {
            ObjectAnimator.ofFloat(binding.fabSearchStudy, "translationY", -130f).apply{start()}
            ObjectAnimator.ofFloat(binding.fabCreateStudy, "translationY", -260f).apply{start()}
            binding.fabMyStudy.setImageResource(R.drawable.ic_close_24)
        }

        isFabOpen = !isFabOpen
    }
}