package com.ssafy.jobis.presentation.study

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.ssafy.jobis.databinding.FragmentMyStudyBinding
import com.ssafy.jobis.presentation.chat.ChatActivity
import com.ssafy.jobis.presentation.study.adapter.MyStudyAdapter

class MyStudyFragment: Fragment() {

    private var _binding: FragmentMyStudyBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentMyStudyBinding.inflate(inflater, container, false)

        val myStudyAdapter = MyStudyAdapter{adapterOnClick()}
        binding.rvMyStudy.adapter = myStudyAdapter

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

    private fun adapterOnClick() {
        val intent = Intent(context, ChatActivity::class.java)
        startActivity(intent)
    }
}