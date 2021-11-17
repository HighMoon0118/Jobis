package com.ssafy.jobis.presentation.chat.adapter
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ssafy.jobis.R
import com.ssafy.jobis.data.model.calendar.Schedule
import com.ssafy.jobis.presentation.chat.viewholder.ChatScheduleViewHolder

class ChatScheduleAdapter(private val datas: ArrayList<Schedule>) :
    RecyclerView.Adapter<ChatScheduleViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatScheduleViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_study_schedule, parent, false)
        return ChatScheduleViewHolder(view)
    }
    override fun onBindViewHolder(holder: ChatScheduleViewHolder, position: Int) {
        holder.bind(datas[position])
    }
    override fun getItemCount(): Int {
        return datas.size
    }

}