package com.ssafy.jobis.presentation.calendar
import android.annotation.SuppressLint
import android.app.Activity
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import com.ssafy.jobis.R
import com.ssafy.jobis.data.model.calendar.CalendarDatabase
import com.ssafy.jobis.data.model.calendar.Schedule
import kotlinx.android.synthetic.main.fragment_single_schedule.*
import kotlinx.android.synthetic.main.fragment_single_schedule.view.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

import java.text.SimpleDateFormat
import java.util.*

class SingleScheduleFragement(val activity: Activity, private val year: Int, val month: Int, val day: Int) : Fragment() {
    private var startDateString =""
    private var startTimeString = ""
    private var endTimeString = ""
    private var startHour = 0
    private var startMinute = 0
    private var startYear = 0
    private var startMonth = 0
    private var startDay = 0
    private var endHour = 0
    private var endMinute = 0
    var title: String = ""
    var content: String= ""
    // 시간 불러오는 두가지 방법
    // 1. System.currentTimeMillis()
    // 2. date()
    // 3. Calendar.getInstance()
    // 각자 쓰이는 메소드가 다른듯? 뭐가 좋을지 모르겠지만 일단 calendar로 했음
    // 어떤 형식으로 가져올지 선언
    @SuppressLint("SimpleDateFormat")
    val dateFormat1 = SimpleDateFormat("yyyy-MM-dd")
    @SuppressLint("SimpleDateFormat")
    val dateFormat2 = SimpleDateFormat("HH:mm")
    private var scheduleStartDate = ""
    private var scheduleStartTime = ""
    private var scheduleEndTime = ""
    val calendar: Calendar = Calendar.getInstance()


    @SuppressLint("SimpleDateFormat")
    fun singleScheduleAddFun():Int{
        // 시작 날짜 따로 안정했으면
        if (startYear == 0) {
            startYear = year
            startMonth = month
            startDay = day
        }
        // 시작 시간 안정했으면 자동 할당
        if (startTimeString == "") {
            startTimeString = scheduleStartTime
        }
        // 끝 시간 안정했으면
        if (endTimeString == "") {
            endTimeString = scheduleEndTime
        }

        // 일정 제목과 내용 받아오기
        title = scheduleTitleEditText.text.toString()
        content = scheduleContentEditText.text.toString()

        Log.d("로그 저장 정보", "$title, $content, $startYear, ${startMonth}, $startDay, $startTimeString, $endTimeString")
        Log.d("시작끝시간", "$startHour, $endHour, $startMinute, $endMinute")
        if (title==""){
            Toast.makeText(context,R.string.empty_title, Toast.LENGTH_SHORT).show()
            return 0
        }
        if (content==""){
            Toast.makeText(context,R.string.empty_content, Toast.LENGTH_SHORT).show()
            return 0
        }

        if (endHour<startHour){
            Toast.makeText(context,R.string.time_select_error, Toast.LENGTH_SHORT).show()
            return 0
        } else if (endHour == startHour && endMinute < startMinute) {
            Toast.makeText(context,R.string.time_select_error, Toast.LENGTH_SHORT).show()
            return 0
        }

        val newSchedule = Schedule(title, content, startYear, startMonth+1, startDay, startTimeString, endTimeString, 0, 0, "")
        val db = CalendarDatabase.getInstance(activity)
        CoroutineScope(Dispatchers.IO).launch {
            db!!.calendarDao().insert(newSchedule)
            val dbList = db!!.calendarDao().getAll()
            println("DB 결과: $dbList")
        }

        return 1
        
    }
    
    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }


    @SuppressLint("SimpleDateFormat")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.d("시작 날짜 전달",  "$year $month $day")
        val view = inflater.inflate(R.layout.fragment_single_schedule, null)


        calendar.add(Calendar.HOUR, 9) // 기준 시간 +9 맞춤
        var currentCal = calendar // Tue Nov 02 15:02:28 GMT 2021
        
        scheduleStartDate = dateFormat1.format(currentCal.time)
        scheduleStartTime = dateFormat2.format(currentCal.time)
        startHour = currentCal.get(Calendar.HOUR_OF_DAY)
        startMinute = currentCal.get(Calendar.MINUTE)
        // 화면 로딩시 보이는 시간! +10분 후가 기본  endTime
        scheduleStartDate = if (day<=9 && month <= 8) {  // 월 일 전부 한자리
            "${year}-0${month}-0${day}"
        } else if (month <= 9) { // 월만 한자리
            "${year}-0${month}-${day}"
        } else if (day <= 9 ){ // 일만 한자리
            "${year}-${month}-0${day}"
        } else { // 처리할 필요 없음
            "${year}-${month}-${day}"
        }

        // 현재 시간 50~59분 이면 59로 강제 지정 -> 안그러면 날짜 변경됨
        if (SimpleDateFormat("HH").format(calendar.time) == "23" ){
            if (SimpleDateFormat("mm").format(calendar.time).toInt() in 50..59){
                scheduleEndTime = "59"
                calendar.set(Calendar.MINUTE, 59)
                val test = calendar.get(Calendar.MINUTE)
                scheduleEndTime = "23:59"
            }
        } else{
            calendar.add(Calendar.MINUTE, 10) // 10분 후 자동 지정
            scheduleEndTime = dateFormat2.format(calendar.time)
        }

        val afterTime = calendar
        scheduleEndTime = dateFormat2.format(afterTime.time)


        view.startDateBtn.text = scheduleStartDate
        view.startTimeBtn.text = scheduleStartTime
        view.endTimeBtn.text = scheduleEndTime

        //시작일 선택 버튼
        view.startDateBtn.setOnClickListener {
            // OnDateSetListener : 사용자가 날짜 선택을 완료했음을 알림, 내부 기능 작성
            // com.example.calendardetail.MainActivity$$ExternalSyntheticLambda0@bb1014f
            val dateSetListener = DatePickerDialog.OnDateSetListener { _, year, month, dayOfMonth ->
                startDateString = if (dayOfMonth<=9 && month <= 8) { // 1~9일 선택하더라도 포맷 맞추려고 설정
                    "${year}-0${month+1}-0${dayOfMonth}"
                } else if (month <= 8) {
                    "${year}-0${month+1}-${dayOfMonth}"
                } else if (dayOfMonth <= 9 ){
                    "${year}-${month+1}-0${dayOfMonth}"
                } else {
                    "${year}-${month + 1}-${dayOfMonth}"
                }
                startYear = year
                startMonth = month
                startDay = dayOfMonth
                startDateBtn.text = startDateString
            }
            // Dialog 창 띄움
            if (startDateString == "") {
                DatePickerDialog(
                    activity,
                    dateSetListener,
                    calendar.get(Calendar.YEAR),
                    calendar.get(Calendar.MONTH),
                    calendar.get(Calendar.DAY_OF_MONTH),
                ).show()
            } else {
                DatePickerDialog(activity, dateSetListener, startYear, startMonth, startDay).show()
            }

        }
        view.startTimeBtn.setOnClickListener {
            val timeSetListener = TimePickerDialog.OnTimeSetListener { _, hourOfDay, minute ->
                startTimeString = if (hourOfDay <= 9) {
                    "0${hourOfDay}:"
                } else {
                    "${hourOfDay}:"
                }
                startTimeString += if (minute <= 9) {
                    "0${minute}"
                } else {
                    "$minute"
                }
                startHour = hourOfDay
                startMinute = minute
                startTimeBtn.text = startTimeString
            }
            if (startTimeString == "") {
                TimePickerDialog(
                    activity,
                    timeSetListener,
                    calendar.get(Calendar.HOUR_OF_DAY),
                    calendar.get(Calendar.MINUTE),
                    true,
                ).show()
            }
            else {
                TimePickerDialog(
                    activity,
                    timeSetListener,
                    startHour,
                    startMinute,
                    true,
                ).show()

            }
        }

//        view.endDateBtn.setOnClickListener {
//            // OnDateSetListener : 사용자가 날짜 선택을 완료했음을 알림, 내부 기능 작성
//            // com.example.calendardetail.MainActivity$$ExternalSyntheticLambda0@bb1014f
//            val dateSetListener = DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
//                if (dayOfMonth<=9 && month <= 8) { // 1~9일 선택하더라도 포맷 맞추려고 설정
//                    endDateString ="${year}-0${month+1}-0${dayOfMonth}"
//                } else if (month <= 8) {
//                    endDateString ="${year}-0${month+1}-${dayOfMonth}"
//                } else if (dayOfMonth <= 9 ){
//                    endDateString ="${year}-${month+1}-0${dayOfMonth}"
//                } else {
//                    endDateString = "${year}-${month + 1}-${dayOfMonth}"
//                }
//                endYear = year
//                endMonth = month
//                endDay = dayOfMonth
//                endDateBtn.setText(endDateString)
//            }
//            // Dialog 창 띄움
//            if (endDateString == "") {
//                DatePickerDialog(activity, dateSetListener, afterTime.get(Calendar.YEAR), afterTime.get(Calendar.MONTH),afterTime.get(Calendar.DAY_OF_MONTH), ).show()
//            } else {
//                DatePickerDialog(activity, dateSetListener, endYear, endMonth, endDay).show()
//            }
//        }

        view.endTimeBtn.setOnClickListener {

            val timeSetListener = TimePickerDialog.OnTimeSetListener { _, hourOfDay, minute ->
                endTimeString = if (hourOfDay <= 9) {
                    "0${hourOfDay}:"
                } else {
                    "${hourOfDay}:"
                }
                endTimeString += if (minute <= 9) {
                    "0${minute}"
                } else {
                    "$minute"
                }
                endHour = hourOfDay
                endMinute = minute
                endTimeBtn.text = endTimeString
            }
            if (endTimeString == "") {
                TimePickerDialog(
                    activity,
                    timeSetListener,
                    afterTime.get(Calendar.HOUR_OF_DAY),
                    afterTime.get(Calendar.MINUTE),
                    true,
                ).show()
            }
            else {
                TimePickerDialog(
                    activity,
                    timeSetListener,
                    endHour,
                    endMinute,
                    true,
                ).show()

            }
        }

        view.singleScheduleAddBtn.setOnClickListener{
        
                if (startYear == 0) {
                    startYear = SimpleDateFormat("yyyy").format(calendar.time).toInt()
                    startMonth = SimpleDateFormat("MM").format(calendar.time).toInt()
                    startDay = SimpleDateFormat("dd").format(calendar.time).toInt()

                }
                if (startTimeString == "") {
                    startTimeString = scheduleStartTime
                    println(startTimeString)

                }
                if (endTimeString == "") {
                    endTimeString = scheduleEndTime
                    println(endTimeString)
                }

                title = view.scheduleTitleEditText.text.toString()
                content = view.scheduleContentEditText.text.toString()

                println("시작, $startTimeString")
                println("끝, $endTimeString")
       
            val newSchedule = Schedule(title, content, startYear, startMonth+1, startDay, startTimeString, endTimeString, 0, 0, "")
            val db = CalendarDatabase.getInstance(activity)
            CoroutineScope(Dispatchers.IO).launch {
                db!!.calendarDao().insert(newSchedule)
                val dbList = db!!.calendarDao().getAll()
                println("DB 결과: $dbList")
            }


//            var newSchedule = Schedule(title, content, startYear, startMonth+1, startDay)
//            var db = CalendarDatabase.getInstance(this.context)
//            CoroutineScope(Dispatchers.IO).launch {
//                db!!.calendarDao().insert(newSchedule)
//                var dbList = db!!.calendarDao().getAll()
//                println("DB 결과: " + dbList)
//            }
            

        }

        view.scheduleList.setOnClickListener {
            println("일정 확인")
            val db = CalendarDatabase.getInstance(this.context)
            CoroutineScope(Dispatchers.IO).launch {
                val dbList = db!!.calendarDao().getAll()
                println("일정 결과: $dbList")
            }
        }
        return view

    }
}
//  프래그먼트 유지  삭제 등 onAttach, onDetach 메서드를 오버라이딩