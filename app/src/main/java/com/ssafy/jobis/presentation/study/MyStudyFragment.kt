package com.ssafy.jobis.presentation.study

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.SystemClock
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.ktx.storage
import com.ssafy.jobis.R
import com.ssafy.jobis.data.model.study.Study
import com.ssafy.jobis.databinding.AlertdialogEdittextBinding
import com.ssafy.jobis.databinding.FragmentMyStudyBinding
import com.ssafy.jobis.presentation.chat.ChatActivity
import com.ssafy.jobis.presentation.study.adapter.MyStudyAdapter
import com.ssafy.jobis.presentation.study.adapter.TmpAdapter
import java.util.ArrayList

// 여기 맞음
class MyStudyFragment: Fragment() {

    private var _binding: FragmentMyStudyBinding? = null
    private val binding get() = _binding!!

//    lateinit var storage: FirebaseStorage
//    private lateinit var database: DatabaseReference
    var database = Firebase.database.reference

    private var studyList = arrayListOf<CreateStudy.Study>()

    var handler: Handler? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentMyStudyBinding.inflate(inflater, container, false)

//        val myStudyAdapter = MyStudyAdapter{adapterOnClick()}
//        binding.rvMyStudy.adapter = myStudyAdapter


        // +버튼을 누르면 스터디 생성으로 넘어가게 하자
        binding.fabMyStudy.setOnClickListener {
           var intent = Intent(context, CreateStudy::class.java)
            startActivity(intent)
        }





        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        fetchStudy()
        Log.d("?", studyList.toString())
    }





    private fun fetchStudy() {
//        val ref = FirebaseDatabase.getInstance().getReference()
        val ref = FirebaseDatabase.getInstance().getReference("/Study")

//        var studyList = arrayListOf<CreateStudy.Study>()

        ref.addListenerForSingleValueEvent(object: ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                for (child in snapshot.children) {
                    val studyName = child.child("studyName").getValue()
                    val studyDescribe = child.child("studyDescribe").getValue()
                    val uid = child.child("uid").getValue()

//                    Log.d("Three", "studyName: ${studyName} studyDescribe: ${studyDescribe} uid: ${uid}")
//                    val study:CreateStudy.Study? = child.getValue(CreateStudy.Study::class.java)
                    val study = CreateStudy.Study(studyName.toString(), studyDescribe.toString(), uid.toString())
                    studyList.add(study!!)
                    Log.d("???", studyList.toString())
//                    Log.d("study", study.toString())
                    // 여기서 어댑터에 하나씩 넣어줘야하는데??
                }

//                Log.d("studyList", studyList.toString())
                val tmpAdapter = TmpAdapter(studyList)
                binding.rvMyStudy.adapter = tmpAdapter

            }


            override fun onCancelled(error: DatabaseError) {
            }

        })
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