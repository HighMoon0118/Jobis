package com.ssafy.jobis.presentation.chat

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.widget.ScrollView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.*
import com.google.firebase.ktx.Firebase
import com.ssafy.jobis.R
import com.ssafy.jobis.data.model.study.Chat
import com.ssafy.jobis.data.model.study.StudyDatabase
import com.ssafy.jobis.databinding.ActivityChatLogBinding
import com.ssafy.jobis.presentation.study.adapter.ChatDisplayAdapter
import com.ssafy.jobis.presentation.study.adapter.TmpAdapter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.HashMap

class ChatLogActivity : AppCompatActivity() {
    private var mBinding:ActivityChatLogBinding? = null
    private val binding get() = mBinding!!

    private val mAuth = FirebaseAuth.getInstance()

    private val user =  Firebase.auth.currentUser


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mBinding = ActivityChatLogBinding.inflate(layoutInflater)

        setContentView(binding.root)

        val intent = getIntent()
        val studyName = intent.getStringExtra("studyTitle").toString()
        val study_id = intent.getStringExtra("study_id").toString()



        val db = StudyDatabase.getInstance(this@ChatLogActivity)
        db!!.getStudyDao().getChatList(study_id.toInt()).observe(this, {
            val chatDisplayAdapter = ChatDisplayAdapter(it)
            binding.groupChatDisplay.adapter = chatDisplayAdapter

        })



        binding.sendBtn.setOnClickListener {
            // db에 chat 내용 저장
            val uid = Firebase.auth.currentUser!!.uid
            val content = binding.inputGroupMessage.text.toString()
            val created_at = SimpleDateFormat("hh:mm a").toString()

            val chat = Chat(uid = uid, content = content, created_at =  created_at, study_id = study_id.toInt())

            CoroutineScope(Dispatchers.IO).launch {
            val db = StudyDatabase.getInstance(this@ChatLogActivity)
                db!!.getStudyDao().insertChat(chat)
            }

            // 입력칸 빈칸으로 만듬
            binding.inputGroupMessage.setText(null)

            // 스크롤 최하단
            binding.myScrollView.fullScroll(ScrollView.FOCUS_DOWN)
        }
    }

}