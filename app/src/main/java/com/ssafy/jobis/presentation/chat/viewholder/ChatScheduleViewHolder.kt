package com.ssafy.jobis.presentation.chat.viewholder

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ssafy.jobis.R
import com.ssafy.jobis.data.model.calendar.Schedule

class ChatScheduleViewHolder(view: View) : RecyclerView.ViewHolder(view){
    private val schedule_date : TextView = itemView.findViewById(R.id.study_schedule_date)
    private val schedule_title : TextView = itemView.findViewById(R.id.study_schedule_title)
    private val schedule_content : TextView = itemView.findViewById(R.id.study_schedule_content)
    private val schedule_time : TextView = itemView.findViewById(R.id.study_schedule_time)

    fun bind(item: Schedule) {
        var year = item.year
        var month = item.month
        var day = item.day
        var txt = "${year}년 ${month}월 ${day}일"
        var title = item.title
        var content = item.content
        var time = "${item.start_time} to ${item.end_time}"
        schedule_date.text = txt
        schedule_title.text = title
        schedule_content.text = content
        schedule_time.text = time
    }
}