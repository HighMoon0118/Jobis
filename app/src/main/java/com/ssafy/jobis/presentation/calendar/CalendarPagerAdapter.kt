package com.ssafy.jobis.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ssafy.jobis.databinding.CalendarViewpagerBinding

class CalendarPagerViewHolder(val binding: CalendarViewpagerBinding) :
        RecyclerView.ViewHolder(binding.root)

class CalendarPagerAdapter(private val dates: ArrayList<Int>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):
            RecyclerView.ViewHolder = CalendarPagerViewHolder(CalendarViewpagerBinding.inflate(
        LayoutInflater.from(parent.context), parent, false))
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val binding = (holder as CalendarPagerViewHolder).binding
        // 뷰에 데이터 출력
        binding.calendarViewContent.text = dates[position].toString()
//        when (position % 3) {
//            0 -> binding.calendarViewContent.setBackgroundColor(Color.RED)
//            1 -> binding.calendarViewContent.setBackgroundColor(Color.BLUE)
//            2 -> binding.calendarViewContent.setBackgroundColor(Color.GREEN)
//        }
    }

    override fun getItemCount(): Int {
        return dates.size
    }

}