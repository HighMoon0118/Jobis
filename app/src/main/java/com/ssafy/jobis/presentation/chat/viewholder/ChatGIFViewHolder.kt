package com.ssafy.jobis.presentation.chat.viewholder

import android.graphics.ImageDecoder
import android.graphics.drawable.AnimatedImageDrawable
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ssafy.jobis.R
import com.ssafy.jobis.presentation.chat.ImgChat
import com.ssafy.jobis.presentation.chat.adapter.ChatAdapter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ChatGIFViewHolder(view: View): RecyclerView.ViewHolder(view) {

    private val img: ImageView = view.findViewById(R.id.img_gif)

    fun bind(imgChat: ImgChat?) {
        if (imgChat == null) return
        val drawable = imgChat.drawable

        img.setImageDrawable(drawable)
        if (!imgChat.isMoved && drawable  is AnimatedImageDrawable) {
            drawable.repeatCount = 4
            drawable.start()
            imgChat.isMoved = true

            img.setOnClickListener {
                drawable.repeatCount = 4
                drawable.start()
            }
        }

    }
}