package com.ssafy.jobis.presentation.chat.viewmodel

import android.annotation.SuppressLint
import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.ssafy.jobis.data.model.study.Chat
import com.ssafy.jobis.data.model.study.Study
import com.ssafy.jobis.data.model.study.StudyWithChats
import com.ssafy.jobis.presentation.chat.ChatRepository
import com.ssafy.jobis.presentation.chat.MyFCMService.Companion.currentStudyId
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class ChatViewModel(application: Application): AndroidViewModel(application) {

    private val _studyWithChats: LiveData<StudyWithChats>
    val studyWithChats: LiveData<StudyWithChats> get() = _studyWithChats
    var fileReference: String = ""

    private val repo = ChatRepository(application)

    init {
        Log.d("현재 스터디", currentStudyId)
        _studyWithChats = repo.getChatList(currentStudyId)!!
    }

    @SuppressLint("SimpleDateFormat")
    fun sendMessage(studyId: String, userId: String, content: String) {

        val date = Date(System.currentTimeMillis())
        val createdAt = SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(date)

        saveMessage(studyId, userId, content, createdAt)
        uploadMessage(studyId, userId, content, createdAt)
    }

    fun saveMessage(studyId: String, userId: String, content: String, createdAt: String) {
        CoroutineScope(Dispatchers.IO).launch {
            repo.saveMessage(studyId, userId, content, createdAt)
        }
    }

    fun uploadMessage(studyId: String, userId: String, content: String, createdAt: String) {
        CoroutineScope(Dispatchers.IO).launch {
            repo.uploadMessage(studyId, userId, content, createdAt)
        }
    }

}