package com.example.jobis.presentation.chat.viewholder

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.jobis.R

class ChatHeaderViewHolder(view: View): RecyclerView.ViewHolder(view) {

    private val chatTitle: TextView = view.findViewById(R.id.tv_chat_title)

    fun bind(title: String) {
        chatTitle.text = title
    }
}