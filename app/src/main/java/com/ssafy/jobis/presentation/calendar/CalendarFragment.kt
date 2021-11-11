package com.ssafy.jobis.presentation.calendar

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.ssafy.jobis.databinding.FragmentCalendarBinding
import com.ssafy.jobis.presentation.CalendarPagerAdapter
import com.ssafy.jobis.presentation.CalendarScheduleActivity
import com.ssafy.jobis.presentation.calendar.decorators.TextDecorator
import com.ssafy.materialcalendar.OneDayDecorator
import com.ssafy.materialcalendar.SaturdayDecorator
import com.ssafy.materialcalendar.SundayDecorator
import com.prolificinteractive.materialcalendarview.CalendarDay
import com.prolificinteractive.materialcalendarview.MaterialCalendarView
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener
import com.prolificinteractive.materialcalendarview.OnMonthChangedListener
import com.prolificinteractive.materialcalendarview.format.TitleFormatter
import com.ssafy.jobis.data.model.calendar.CalendarDatabase
import com.ssafy.jobis.data.model.calendar.Schedule
import com.ssafy.materialcalendar.EventDecorator
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
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
        // 버튼 연결 test
        binding.calendarBtn.setOnClickListener {
            val intent = Intent(activity, CalendarScheduleActivity::class.java)
            startActivity(intent)
        }

        // room 데이터 추가 test용
        binding.roomTest.setOnClickListener {
            var newSchedule = Schedule("할 일", "2022 서류접수", 2021, 11, 11, "09:00", "18:00", -1, -1, "삼성전기")
            var db = CalendarDatabase.getInstance(this.context)
            CoroutineScope(Dispatchers.IO).launch {
                db!!.calendarDao().insert(newSchedule)
                var dbList = db!!.calendarDao().getAll()
                println("DB 결과: " + dbList)
            }
        }

        // 캘린더 레이아웃
        var calendar = binding.calendarView

        // 0. room에서 데이터 가져와서 점 찍어주기
        var scheduleData = CalendarDatabase.getInstance(this.context)
        CoroutineScope(Dispatchers.IO).launch {
            var scheduleList = scheduleData!!.calendarDao().getAll()
            if (scheduleList.size != 0) { // schedule이 있을 때만..
                var dates = ArrayList<CalendarDay>() // 점을 찍을 날짜들을 여기에 add로 담아주면 됨
                for (i in 0..scheduleList.size-1) {
                    var dot_year = scheduleList[i].year
                    var dot_month = scheduleList[i].month-1
                    var dot_day = scheduleList[i].day
                    println("DB결과: " + scheduleList[i])
                    // 점 찍기 => 여러 날에 표시하려고 days를 구성해서 추가해주는 방식..
                    // 달력에 표시할 날짜 가져오기
                    var date = Calendar.getInstance()
                    date.set(dot_year, dot_month, dot_day)

                    // 달력에 표시할 날짜 day List에 넣기
                    var day = CalendarDay.from(date) // Calendar 자료형을 넣어주면 됨
                    dates.add(day)

                    // 글자 표시는 하루하루 해줘야 함
                    var date_for_text = ArrayList<CalendarDay>()
                    date_for_text.add(day)
                    calendar.addDecorator(TextDecorator(date_for_text, scheduleList[i].title))
                }
                calendar.addDecorator(EventDecorator(Color.parseColor("#3f51b5"), dates)) // 점 찍기
            }
        }

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


        // 뷰페이저2 사용
        // 첫 화면에서 보여줄 달의 정보를 가지고 있는 뷰를 여기서 만들어줘야 한다.
        var calendarDates = ArrayList<ArrayList<Schedule>>() // 각 날짜의 스케줄들을 담고 있는 List<Schedule>을 원소로 하는 ArrayList
        val calc = Calendar.getInstance()
        var lastDay = calc.getActualMaximum(Calendar.DAY_OF_MONTH)

        var currentMonth = calc.get(Calendar.MONTH)
        var currentYear = calc.get(Calendar.YEAR)
        CoroutineScope(Dispatchers.IO).launch {
            var scheduleList = scheduleData!!.calendarDao().getAll() // 모든 일정 가져오기 [Schedule, Schedule, Schedule, ...]
            for (i in 1..lastDay) {
                var temp_schedule = ArrayList<Schedule>()
                // calendarDates의 원소를 하나하나 만들어 넣을 것임
                // 1. 해당 날짜에 스케줄이 있을 때
                if (scheduleList.size > 0) {
                    for (j in 0..scheduleList.size-1) {
                        println("scheduleListJ: " + scheduleList[j].month)
                        println("Month: " + currentMonth)
                        if (currentYear == scheduleList[j].year && currentMonth == scheduleList[j].month-1 && i == scheduleList[j].day) {
                            temp_schedule.add(scheduleList[j])
                        }
                    }
                }
                // 2. 해당 날짜에 아무 스케줄이 없을 때, 일정 없음이 표시된 객체를 넣어줌
                if (temp_schedule.size == 0) {
<<<<<<< HEAD
                    val empty_schedule = Schedule("일정 없음", "", currentYear, currentMonth, i, "", "", -1, -1, "")
=======
                    val empty_schedule = Schedule("일정 없음", "", currentYear, currentMonth, i , "10:00", "12:00")
>>>>>>> 65ee86d1330e65ad3b5e9b7d09c61c96bddceed6
                    temp_schedule.add(empty_schedule)
                }
                calendarDates.add(temp_schedule) // 하루하루 일정들을 모두 추가
            }
            println("일정: " + calendarDates)
        }


        // 처음 보여줄 날짜
        var firstYear = calc.get(Calendar.YEAR)
        var firstMonth = calc.get(Calendar.MONTH)
        binding.calendarViewpager.adapter = CalendarPagerAdapter(calendarDates)
        binding.calendarViewpager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                calc.set(firstYear, firstMonth, position+1)
                // you are on the first page
                binding.calendarView.setSelectedDate(calc)
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
        var calendarDates = ArrayList<ArrayList<Schedule>>()
        var year = date?.year
        var month = date?.month
        var day = date?.day
        var calc = Calendar.getInstance()
        calc.set(year!!, month!!, day!!)
        var lastDay = calc.getActualMaximum(Calendar.DAY_OF_MONTH)

        var scheduleData = CalendarDatabase.getInstance(this.context)
        CoroutineScope(Dispatchers.IO).launch {
            var scheduleList = scheduleData!!.calendarDao().getAll() // 모든 일정 가져오기 [Schedule, Schedule, Schedule, ...]
            for (i in 1..lastDay) {
                var temp_schedule = ArrayList<Schedule>()
                // calendarDates의 원소를 하나하나 만들어 넣을 것임
                // 1. 해당 날짜에 스케줄이 있을 때
                if (scheduleList.size > 0) {
                    for (j in 0..scheduleList.size-1) {
                        if (year == scheduleList[j].year && month == scheduleList[j].month-1 && i == scheduleList[j].day) {
                            temp_schedule.add(scheduleList[j])
                        }
                    }
                }
                // 2. 해당 날짜에 아무 스케줄이 없을 때, 일정 없음이 표시된 객체를 넣어줌
                if (temp_schedule.size == 0) {
<<<<<<< HEAD
                    val empty_schedule = Schedule("일정 없음", "", year, month, i, "", "",-1, -1, "")
=======
                    val empty_schedule = Schedule("일정 없음", "", year, month, i, "10:00", "12:00" )
>>>>>>> 65ee86d1330e65ad3b5e9b7d09c61c96bddceed6
                    temp_schedule.add(empty_schedule)
                }
                calendarDates.add(temp_schedule) // 하루하루 일정들을 모두 추가
            }
        }
        binding.calendarViewpager.adapter = CalendarPagerAdapter(calendarDates)
        binding.calendarViewpager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                calc.set(year, month, position+1) // position은 0부터 시작, 날짜는 1부터 시작하므로
                // you are on the first page
                binding.calendarView.setSelectedDate(calc)

            }
        })
    }

    override fun onDateSelected(
        widget: MaterialCalendarView,
        date: CalendarDay,
        selected: Boolean
    ) {
        // 지금 11월달이 10월로, 1월달이 0월로 표기된다. Month에 +1을 하고 보여줘야 함
        var selectedDay = date.day
        var selectedMonth = date.month + 1
        var selectedYear = date.year
        if (selected) {
            binding.calendarViewpager.setCurrentItem(selectedDay-1) // 선택한 날짜로 이동
        }
    }
}
