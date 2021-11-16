package com.ssafy.jobis.presentation.chat.viewmodel

import androidx.lifecycle.ViewModel
import com.ssafy.jobis.presentation.chat.ChatRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ChatViewModel: ViewModel() {

    private val repo = ChatRepository()

    fun sendMessage(roomId: String, userId: String, content: String) {
        CoroutineScope(Dispatchers.IO).launch {
            repo.sendMessage(roomId, userId, content)
        }
    }
}