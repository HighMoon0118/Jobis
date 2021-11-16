package com.ssafy.jobis.presentation.study

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.LinearLayoutManager
import com.ssafy.jobis.data.model.study.Study
import com.ssafy.jobis.data.model.study.StudyDatabase
import com.ssafy.jobis.databinding.FragmentMyStudyBinding
import com.ssafy.jobis.presentation.chat.ChatActivity
import com.ssafy.jobis.presentation.study.adapter.MyStudyAdapter
import com.ssafy.jobis.presentation.study.adapter.TmpAdapter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MyStudyFragment: Fragment() {

    private var _binding: FragmentMyStudyBinding? = null
    private val binding get() = _binding!!

//    private lateinit var studyList:LiveData<List<Study>>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentMyStudyBinding.inflate(inflater, container, false)


        binding.fabMyStudy.setOnClickListener {
            val intent = Intent(context, CreateStudy::class.java)
            startActivity(intent)
        }

//        fetchStudy()
//        val db = StudyDatabase.getInstance(requireContext())
//        val studyList = db!!.getStudyDao().getAllStudy()
//
//        val tmpAdapter = TmpAdapter(studyList)
//        binding.rvMyStudy.adapter = tmpAdapter

//        CoroutineScope(Dispatchers.IO).launch {
//            val db = StudyDatabase.getInstance(requireContext())
//            var studyList = db!!.getStudyDao().getAllStudy()
//
//            val tmpAdapter = TmpAdapter(studyList)
//            binding.rvMyStudy.adapter = tmpAdapter
//        }




//        val db = StudyDatabase.getInstance(requireContext())
//        db!!.getStudyDao().getAllStudy().observe(viewLifecycleOwner, {
//            binding.tvTmp.text = it.toString()
//        })

        var db = StudyDatabase.getInstance(requireContext())

        db!!.getStudyDao().getAllStudy().observe(viewLifecycleOwner, {
            val tmpAdapter = TmpAdapter(it)
            Log.d("tmpAdapter", tmpAdapter.toString())
            binding.rvMyStudy.adapter = tmpAdapter
        })

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

    private fun fetchStudy() {
        CoroutineScope(Dispatchers.IO).launch {
            val db = StudyDatabase.getInstance(requireContext())
            val studyList = db!!.getStudyDao().getAllStudy()

//            val tmpAdapter = TmpAdapter(studyList)
//            binding.rvMyStudy.adapter = tmpAdapter

        }

    }
}