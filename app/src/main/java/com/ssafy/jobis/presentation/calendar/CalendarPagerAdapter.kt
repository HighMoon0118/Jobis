package com.ssafy.jobis.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ssafy.jobis.data.model.calendar.Schedule
import com.ssafy.jobis.databinding.CalendarViewpagerBinding

class CalendarPagerViewHolder(val binding: CalendarViewpagerBinding) :
        RecyclerView.ViewHolder(binding.root)

class CalendarPagerAdapter(private val dates: ArrayList<ArrayList<Schedule>>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):
            RecyclerView.ViewHolder = CalendarPagerViewHolder(CalendarViewpagerBinding.inflate(
        LayoutInflater.from(parent.context), parent, false))
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val binding = (holder as CalendarPagerViewHolder).binding
        // 뷰에 데이터 출력
        var txt = dates[position][0].year.toString() + dates[position][0].month.toString() + dates[position][0].day.toString()
        binding.calendarViewDate.text = txt
        binding.calendarViewTitle.text = dates[position][0].title // 이런 식으로 사용해야 함
        binding.calendarViewContent.text = dates[position][0].content
    }

    override fun getItemCount(): Int {
        return dates.size
    }

}