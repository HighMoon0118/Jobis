package com.example.jobis.presentation.chat.viewholder

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.jobis.R

class ChatViewHolder(view: View): RecyclerView.ViewHolder(view) {

    private val chatText: TextView = view.findViewById(R.id.tv_chat_msg)

    fun bind(text: String) {
        chatText.text = text
    }
}