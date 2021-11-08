package com.ssafy.jobis.presentation.chat.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ssafy.jobis.R
import com.ssafy.jobis.presentation.chat.viewholder.ChatViewHolder

class ChatAdapter: RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    val CHAT_ITEM = 1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            CHAT_ITEM -> ChatViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_chat, parent, false))
            else -> ChatViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_chat, parent, false))
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is ChatViewHolder -> {
                holder.bind("안녕 안녕하세요")
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (position) {
            else -> CHAT_ITEM
        }
    }
    override fun getItemCount(): Int {
        return 20
    }
}