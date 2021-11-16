package com.ssafy.jobis.presentation.chat.viewholder

import android.graphics.ImageDecoder
import android.graphics.drawable.AnimatedImageDrawable
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ssafy.jobis.R
import com.ssafy.jobis.presentation.chat.adapter.ChatAdapter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ChatGIFViewHolder(view: View): RecyclerView.ViewHolder(view) {

    private val img: ImageView = view.findViewById(R.id.img_gif)

    fun bind(studyChat: ChatAdapter.StudyChat) {
        img.setImageDrawable(studyChat.drawable)
        if (!studyChat.isMoved && studyChat.drawable is AnimatedImageDrawable) {
            studyChat.isMoved = true
            studyChat.drawable.repeatCount = 4
            studyChat.drawable.start()
        }
        img.setOnClickListener {
            if (studyChat.drawable is AnimatedImageDrawable) {
                studyChat.drawable.repeatCount = 4
                studyChat.drawable.start()
            }
        }
    }
}