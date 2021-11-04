package com.example.jobis.presentation.calendar

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.jobis.R
import com.example.jobis.databinding.FragmentCalendarBinding
import com.example.materialcalendar.EventDecorator
import com.example.materialcalendar.OneDayDecorator
import com.example.materialcalendar.SaturdayDecorator
import com.example.materialcalendar.SundayDecorator
import com.prolificinteractive.materialcalendarview.CalendarDay
import com.prolificinteractive.materialcalendarview.MaterialCalendarView
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener
import com.prolificinteractive.materialcalendarview.OnMonthChangedListener
import com.prolificinteractive.materialcalendarview.format.TitleFormatter
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class CalendarFragment: Fragment(), OnMonthChangedListener, OnDateSelectedListener {

    private var _binding: FragmentCalendarBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentCalendarBinding.inflate(inflater, container, false)

        // 캘린더 레이아웃
        var calendar = binding.calendarView

        // 1. 맨 처음 달력 "yyyy년 yy월"로 표기하기
        calendar.setTitleFormatter(TitleFormatter {
            val simpleDateFormat = SimpleDateFormat("yyyy년 MM월", Locale.KOREA)
            simpleDateFormat.format(Date().time)
        })

        // 달력 넘기면 동작하는 리스너
        calendar.setOnMonthChangedListener(this)

        // 날짜 선택하면 동작하는 리스너
        calendar.setOnDateChangedListener(this)

        // 토요일, 일요일 색칠
        calendar.addDecorators(SundayDecorator(), SaturdayDecorator(), OneDayDecorator())

        // 점 찍기 => 여러 날에 표시하려고 days를 구성해서 추가해주는 방식..
        var currentDay = Calendar.getInstance() // 오늘 날짜에 점을 찍겠다면?
        var dates = ArrayList<CalendarDay>() // 점을 찍을 날짜들을 여기에 add로 담아주면 됨
        val day = CalendarDay.from(currentDay) // Calendar 자료형을 넣어주면 됨
        dates.add(day)
        calendar.addDecorator(EventDecorator(Color.parseColor("#3f51b5"), dates))

        // 밑에 텍스트 표시(다음엔 뷰페이저 적용해야 함)
        var textView = binding.calendarText
        textView.setText("선택 없음")

        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }


    // 1. 달을 바꿨을 때 "yyyy년 yy월" 형태로 표기하기
    override fun onMonthChanged(widget: MaterialCalendarView?, date: CalendarDay?) {
        widget?.setTitleFormatter(TitleFormatter {
            val simpleDateFormat = SimpleDateFormat("yyyy년 MM월", Locale.KOREA)
            simpleDateFormat.format(date?.date?.time)
        })
    }

    override fun onDateSelected(
        widget: MaterialCalendarView,
        date: CalendarDay,
        selected: Boolean
    ) {
        var textView = binding.calendarText
        if (selected) {
            textView.setText(date.toString())
        } else {
            textView.setText("선택 없음")
        }
    }
}