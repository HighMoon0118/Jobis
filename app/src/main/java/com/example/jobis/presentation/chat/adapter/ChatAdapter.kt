package com.example.jobis.presentation.chat.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.jobis.R
import com.example.jobis.presentation.chat.viewholder.ChatHeaderViewHolder
import com.example.jobis.presentation.chat.viewholder.ChatViewHolder

class ChatAdapter: RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    val CHAT_HEADER = 0
    val CHAT_ITEM = 1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            CHAT_HEADER -> ChatHeaderViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_chat_header, parent, false))
            else -> ChatViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_chat, parent, false))
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is ChatHeaderViewHolder -> {
                holder.bind("대전알고리즘 스터디")
            }
            is ChatViewHolder -> {
                holder.bind("안녕안녕")
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (position) {
            0 -> CHAT_HEADER
            else -> CHAT_ITEM
        }
    }
    override fun getItemCount(): Int {
        return 5
    }
}