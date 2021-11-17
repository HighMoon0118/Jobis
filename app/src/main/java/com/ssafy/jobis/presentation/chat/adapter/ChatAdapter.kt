package com.ssafy.jobis.presentation.chat.adapter

import android.app.Activity
import android.graphics.ImageDecoder
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.ssafy.jobis.R
import com.ssafy.jobis.data.model.study.Chat
import com.ssafy.jobis.presentation.chat.ImgChat
import com.ssafy.jobis.presentation.chat.viewholder.ChatGIFViewHolder
import com.ssafy.jobis.presentation.chat.viewholder.ChatViewHolder
import java.util.HashMap

class ChatAdapter(val chatList: List<Chat>?, val map: HashMap<Int, ImgChat?>): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    interface onAddedChatListener {
        fun onAddedChat()
    }
//    private val chatList = ArrayList<StudyChat>()

    val CHAT_ITEM = 1
    val GIF_ITEM = 2

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            CHAT_ITEM -> ChatViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_chat, parent, false))
            GIF_ITEM -> ChatGIFViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_chat_gif, parent, false))
            else -> ChatViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_chat, parent, false))
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is ChatViewHolder -> {
                if (chatList != null)
                    holder.bind(chatList[position].content)
            }
            is ChatGIFViewHolder -> {
                holder.bind(map[position])
            }
        }
    }

    override fun getItemViewType(position: Int): Int {

        if (chatList != null) {
            return if (chatList[position].file_name.isNotEmpty()) {
                GIF_ITEM
            } else {
                CHAT_ITEM
            }
        }
        return 1
    }

    override fun getItemCount(): Int {
        return chatList?.size?:0
    }

//    fun addChat(isGif: Boolean, source: ImageDecoder.Source?, content: String?) {
//        val drawable = if (source != null) ImageDecoder.decodeDrawable(source!!) else null
//        // 리사이클러뷰에서 새로운 아이템이 들어오면 기존의 아이템이 위에 새로 생긴 뷰 홀더로 이동하기 때문에
//        // 뷰 홀더안에 gif파일이 한번 움직였었는지 체크하는 boolean값을 넣게 되면
//        // 새로운 아이템이 기존에 있던 뷰 홀더로 들어가기 때문에 gif파일이 움직이지 않게 된다.
//        // 따라서, 아이템에 들어갈 데이터 클래스에 drawable 객체를 만들어 넣어주면 움직이는 중에 새로 아이템이 추가되더라도
//        // 움직이는 상태로 이동하게 된다.
//        chatList.add(StudyChat(isGif, drawable, content))
//        notifyDataSetChanged()
//        if (activity is onAddedChatListener) {
//            activity.onAddedChat()
//        }
//    }

//    data class StudyChat(
//        val isGif: Boolean = false,
//        val drawable: Drawable? = null,
//        val content: String? = null,
//        var isMoved: Boolean = false
//    )
}