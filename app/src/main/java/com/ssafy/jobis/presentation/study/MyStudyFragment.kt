package com.ssafy.jobis.presentation.study

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.ssafy.jobis.databinding.FragmentMyStudyBinding
import com.ssafy.jobis.presentation.study.adapter.MyStudyAdapter

class MyStudyFragment(val myContext: Context): Fragment() {

    private var _binding: FragmentMyStudyBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: StudyViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,savedInstanceState: Bundle?): View? {

        _binding = FragmentMyStudyBinding.inflate(inflater, container, false)

        activity?.run {
            viewModel = ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory(this.application)).get(StudyViewModel::class.java)
        }

        viewModel.studyList.observe(viewLifecycleOwner,  {
            val studyAdapter = MyStudyAdapter(myContext, it)
            binding.rvMyStudy.adapter = studyAdapter
        })

        binding.fabMyStudy.setOnClickListener {
            val intent = Intent(context, CreateStudy::class.java)
            startActivity(intent)
        }

        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}