package com.example.jobis.presentation.calendar

import android.content.DialogInterface
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.NumberPicker
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.example.jobis.R
import com.example.jobis.databinding.FragmentCalendarBinding
import com.example.jobis.presentation.CalendarPagerAdapter
import com.example.jobis.presentation.calendar.decorators.TextDecorator
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
        // calendar.addDecorator(EventDecorator(Color.parseColor("#3f51b5"), dates)) // 점 찍기
        calendar.addDecorator(TextDecorator(dates, "채용공고1"))


        // 밑에 텍스트 표시(다음엔 뷰페이저 적용해야 함)
        var textView = binding.calendarText
        textView.setText("선택 없음")

        // 뷰페이저2 사용
        // 첫 화면에서 보여줄 달의 정보를 가지고 있는 뷰를 여기서 만들어줘야 한다.
        var calendarDates = ArrayList<Int>()
        for (i: Int in 1..31)
            calendarDates.add(i)
        binding.calendarViewpager.adapter = CalendarPagerAdapter(calendarDates)
        binding.calendarViewpager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                if (position == 0) {
                    var cal = Calendar.getInstance()
                    cal.set(2021, 10, 1)
                    // you are on the first page
                    binding.calendarView.setSelectedDate(cal)
                }
                else if (position == 1) {
                    // you are on the second page
                    var cal = Calendar.getInstance()
                    cal.set(2021, 10, 2)
                    binding.calendarView.setSelectedDate(cal)
                }
            }
        })

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
        // 뷰페이저도 초기화 해주고 정보 다시 가져와야 한다
        // 코드 추가
    }

    override fun onDateSelected(
        widget: MaterialCalendarView,
        date: CalendarDay,
        selected: Boolean
    ) {
        var textView = binding.calendarText
        // 지금 11월달이 10월로, 1월달이 0월로 표기된다. Month에 +1을 하고 보여줘야 함
        var selectedDay = date.day
        var selectedMonth = date.month + 1
        var selectedYear = date.year
        if (selected) {
            textView.setText("$selectedYear" + "년" + "$selectedMonth" + "월" + "$selectedDay" + "일")
            binding.calendarViewpager.setCurrentItem(selectedDay-1) // 선택한 날짜로 이동
        } else {
            textView.setText("선택 없음")
        }
    }
}
