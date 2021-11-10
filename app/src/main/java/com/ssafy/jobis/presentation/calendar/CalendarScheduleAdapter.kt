package com.ssafy.jobis.presentation.calendar

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ssafy.jobis.R
import com.ssafy.jobis.data.model.calendar.Schedule

class CalendarScheduleAdapter(private val datas: ArrayList<Schedule>) : RecyclerView.Adapter<CalendarScheduleAdapter.ViewHolder>() {
    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private var title: TextView = itemView.findViewById(R.id.schedule_title)
        private var time: TextView = itemView.findViewById(R.id.schedule_time)
        private var content: TextView = itemView.findViewById(R.id.schedule_content)

        fun bind(item: Schedule) {
            title.text = item.title
            time.text = "DB에 아직 저장하지 않음"
            content.text = item.content
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_schedule, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(datas[position])
    }

    override fun getItemCount(): Int {
        return datas.size
    }
}