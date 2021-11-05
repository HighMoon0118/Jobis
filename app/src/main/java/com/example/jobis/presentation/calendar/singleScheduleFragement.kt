package com.example.jobis.presentation

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import com.example.jobis.R
import kotlinx.android.synthetic.main.fragment_single_schedule.*
import kotlinx.android.synthetic.main.fragment_single_schedule.view.*

import java.text.SimpleDateFormat
import java.util.*

class singleScheduleFragement : Fragment() {
    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }
    lateinit var calendarScheduleActivity: CalendarScheduleActivity

    override fun onAttach(context: Context) { super.onAttach(context)
        // 2. Context를 액티비티로 형변환해서 할당
        calendarScheduleActivity = context as CalendarScheduleActivity }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_single_schedule, null)
        //
        var startDateString =""
        var startTimeString = ""
        var endDateString =""
        var endTimeString = ""
        var startHour = 0
        var startMinute = 0
        var startYear = 0
        var startMonth = 0
        var startDay = 0
        var endYear = 0
        var endMonth = 0
        var endDay = 0
        var endHour = 0
        var endMinute = 0

        // 시간 불러오는 두가지 방법
        // 1. System.currentTimeMillis()
        // 2. date()
        // 3. Calendar.getInstance()
        // 각자 쓰이는 메소드가 다른듯? 뭐가 좋을지 모르겠지만 일단 calendar로 했음
        // 어떤 형식으로 가져올지 선언
        val dateFormat1 = SimpleDateFormat("yyyy-MM-dd")
        val dateFormat2 = SimpleDateFormat("HH:mm")

        val calendar = Calendar.getInstance()
        //java.util.GregorianCalendar[time=1635832948816,areFieldsSet=true,areAllFieldsSet=true,lenient=true,zone=java.util.SimpleTimeZone[id=GMT,offset=0,dstSavings=3600000,useDaylight=false,startYear=0,startMode=0,startMonth=0,startDay=0,startDayOfWeek=0,startTime=0,startTimeMode=0,endMode=0,endMonth=0,endDay=0,endDayOfWeek=0,endTime=0,endTimeMode=0],firstDayOfWeek=1,minimalDaysInFirstWeek=1,ERA=1,YEAR=2021,MONTH=10,WEEK_OF_YEAR=45,WEEK_OF_MONTH=1,DAY_OF_MONTH=2,DAY_OF_YEAR=306,DAY_OF_WEEK=3,DAY_OF_WEEK_IN_MONTH=1,AM_PM=0,HOUR=6,HOUR_OF_DAY=6,MINUTE=2,SECOND=28,MILLISECOND=816,ZONE_OFFSET=0,DST_OFFSET=0]

        calendar.add(Calendar.HOUR, 9) // 기준 시간 +9 맞춤
        // java.util.GregorianCalendar[time=1635865348813,areFieldsSet=true,areAllFieldsSet=true,lenient=true,zone=java.util.SimpleTimeZone[id=GMT,offset=0,dstSavings=3600000,useDaylight=false,startYear=0,startMode=0,startMonth=0,startDay=0,startDayOfWeek=0,startTime=0,startTimeMode=0,endMode=0,endMonth=0,endDay=0,endDayOfWeek=0,endTime=0,endTimeMode=0],firstDayOfWeek=1,minimalDaysInFirstWeek=1,ERA=1,YEAR=2021,MONTH=10,WEEK_OF_YEAR=45,WEEK_OF_MONTH=1,DAY_OF_MONTH=2,DAY_OF_YEAR=306,DAY_OF_WEEK=3,DAY_OF_WEEK_IN_MONTH=1,AM_PM=1,HOUR=3,HOUR_OF_DAY=15,MINUTE=2,SECOND=28,MILLISECOND=813,ZONE_OFFSET=0,DST_OFFSET=0]

        var currentTime = calendar // Tue Nov 02 15:02:28 GMT 2021
        var scheduleStartDate = dateFormat1.format(currentTime.time)
        var scheduleStartTime = dateFormat2.format(currentTime.time)

        calendar.add(Calendar.MINUTE, 10) // 10분 후
        var afterTime = calendar
        var scheduleEndDate = dateFormat1.format(afterTime.time)
        var scheduleEndTime = dateFormat2.format(afterTime.time)


        // 화면 로딩시 보이는 시간! +10분 후가 기본  endTime
        view.startDateBtn.setText(scheduleStartDate)
        view.startTimeBtn.setText(scheduleStartTime)
        view.endDateBtn.setText(scheduleEndDate)
        view.endTimeBtn.setText(scheduleEndTime)
        //
        view.startDateBtn.setOnClickListener {
            var cal = Calendar.getInstance() // 캘린더 뷰 만들기
            // java.util.GregorianCalendar[time=1635835942840,areFieldsSet=true,areAllFieldsSet=true,lenient=true,zone=java.util.SimpleTimeZone[id=GMT,offset=0,dstSavings=3600000,useDaylight=false,startYear=0,startMode=0,startMonth=0,startDay=0,startDayOfWeek=0,startTime=0,startTimeMode=0,endMode=0,endMonth=0,endDay=0,endDayOfWeek=0,endTime=0,endTimeMode=0],firstDayOfWeek=1,minimalDaysInFirstWeek=1,ERA=1,YEAR=2021,MONTH=10,WEEK_OF_YEAR=45,WEEK_OF_MONTH=1,DAY_OF_MONTH=2,DAY_OF_YEAR=306,DAY_OF_WEEK=3,DAY_OF_WEEK_IN_MONTH=1,AM_PM=0,HOUR=6,HOUR_OF_DAY=6,MINUTE=52,SECOND=22,MILLISECOND=840,ZONE_OFFSET=0,DST_OFFSET=0]

            // OnDateSetListener : 사용자가 날짜 선택을 완료했음을 알림, 내부 기능 작성
            // com.example.calendardetail.MainActivity$$ExternalSyntheticLambda0@bb1014f
            val dateSetListener = DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
                if (dayOfMonth<=9 && month <= 8) { // 1~9일 선택하더라도 포맷 맞추려고 설정
                    startDateString ="${year}-0${month+1}-0${dayOfMonth}"
                } else if (month <= 8) {
                    startDateString ="${year}-0${month+1}-${dayOfMonth}"
                } else if (dayOfMonth <= 9 ){
                    startDateString ="${year}-${month+1}-0${dayOfMonth}"
                } else {
                    startDateString = "${year}-${month + 1}-${dayOfMonth}"
                }
                startYear = year
                startMonth = month
                startDay = dayOfMonth
                startDateBtn.setText(startDateString)
            }
            // Dialog 창 띄움
            if (startDateString == "") {
                DatePickerDialog(calendarScheduleActivity, dateSetListener, currentTime.get(Calendar.YEAR), currentTime.get(Calendar.MONTH),currentTime.get(Calendar.DAY_OF_MONTH), ).show()
            } else {
                DatePickerDialog(calendarScheduleActivity, dateSetListener, startYear, startMonth, startDay).show()
            }

        }
        view.startTimeBtn.setOnClickListener {
            val cal = Calendar.getInstance()
            val timeSetListener = TimePickerDialog.OnTimeSetListener { view, hourOfDay, minute ->
                if (hourOfDay <= 9) {
                    startTimeString = "0${hourOfDay}:"
                } else {
                    startTimeString = "${hourOfDay}:"
                }
                if (minute <= 9) {
                    startTimeString += "0${minute}"
                } else {
                    startTimeString += "${minute}"
                }
                startHour = hourOfDay
                startMinute = minute
                startTimeBtn.setText(startTimeString)
            }
            if (startTimeString == "") {
                TimePickerDialog(
                    calendarScheduleActivity,
                    timeSetListener,
                    currentTime.get(Calendar.HOUR_OF_DAY),
                    currentTime.get(Calendar.MINUTE),
                    true,
                ).show()
            }
            else {
                TimePickerDialog(
                    calendarScheduleActivity,
                    timeSetListener,
                    startHour,
                    startMinute,
                    true,
                ).show()

            }
        }

        view.endDateBtn.setOnClickListener {
            // OnDateSetListener : 사용자가 날짜 선택을 완료했음을 알림, 내부 기능 작성
            // com.example.calendardetail.MainActivity$$ExternalSyntheticLambda0@bb1014f
            val dateSetListener = DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
                if (dayOfMonth<=9 && month <= 8) { // 1~9일 선택하더라도 포맷 맞추려고 설정
                    endDateString ="${year}-0${month+1}-0${dayOfMonth}"
                } else if (month <= 8) {
                    endDateString ="${year}-0${month+1}-${dayOfMonth}"
                } else if (dayOfMonth <= 9 ){
                    endDateString ="${year}-${month+1}-0${dayOfMonth}"
                } else {
                    endDateString = "${year}-${month + 1}-${dayOfMonth}"
                }
                endYear = year
                endMonth = month
                endDay = dayOfMonth
                endDateBtn.setText(endDateString)
            }
            // Dialog 창 띄움
            if (endDateString == "") {
                DatePickerDialog(calendarScheduleActivity, dateSetListener, afterTime.get(Calendar.YEAR), afterTime.get(Calendar.MONTH),afterTime.get(Calendar.DAY_OF_MONTH), ).show()
            } else {
                DatePickerDialog(calendarScheduleActivity, dateSetListener, endYear, endMonth, endDay).show()
            }
        }

        view.endTimeBtn.setOnClickListener {

            val timeSetListener = TimePickerDialog.OnTimeSetListener { view, hourOfDay, minute ->
                if (hourOfDay <= 9) {
                    endTimeString = "0${hourOfDay}:"
                } else {
                    endTimeString = "${hourOfDay}:"
                }
                if (minute <= 9) {
                    endTimeString += "0${minute}"
                } else {
                    endTimeString += "${minute}"
                }
                endHour = hourOfDay
                endMinute = minute
                endTimeBtn.setText(endTimeString)
            }
            if (endTimeString == "") {
                TimePickerDialog(
                    calendarScheduleActivity,
                    timeSetListener,
                    afterTime.get(Calendar.HOUR_OF_DAY),
                    afterTime.get(Calendar.MINUTE),
                    true,
                ).show()
            }
            else {
                TimePickerDialog(
                    calendarScheduleActivity,
                    timeSetListener,
                    endHour,
                    endMinute,
                    true,
                ).show()

            }
        }

        return view

    }
}
//  프래그먼트 유지  삭제 등 onAttach, onDetach 메서드를 오버라이딩