package com.ssafy.jobis.presentation.chat

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.ssafy.jobis.R
import com.ssafy.jobis.databinding.ActivityChatBinding
import com.ssafy.jobis.databinding.ActivityChatScheduleAddBinding
import java.util.*

class ChatScheduleAddActivity : AppCompatActivity() {
    private lateinit var binding: ActivityChatScheduleAddBinding
    var dateString = ""
    var timeString = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChatScheduleAddBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 기본 시간 설정
        var currentTime = Calendar.getInstance()
        var currentYear = currentTime.get(Calendar.YEAR)
        var currentMonth = currentTime.get(Calendar.MONTH)
        var currentDay = currentTime.get(Calendar.DAY_OF_MONTH)
        var currentDayOfWeek = currentTime.get(Calendar.DAY_OF_WEEK)
        var currentDayOfWeekString = dayOfWeekToString(currentDayOfWeek)
        var currentHour = currentTime.get(Calendar.HOUR_OF_DAY)
        var currentMinute = currentTime.get(Calendar.MINUTE)

        var currentDateString = "${currentYear}. ${currentMonth}. ${currentDay} ${currentDayOfWeekString}"
        var currentTimeString = "${currentHour}:${currentMinute}"

        binding.dateButton.text = currentDateString
        binding.timeButton.text = currentTimeString

        // 버튼 클릭시 datepicker 동작
        binding.dateButton.setOnClickListener {
            var cal = Calendar.getInstance()
            var temp_cal = Calendar.getInstance()
            var dateSetListener = DatePickerDialog.OnDateSetListener {
                view, year, month, dayOfMonth -> temp_cal.set(year, month, dayOfMonth)
                var dayOfWeek = temp_cal.get(Calendar.DAY_OF_WEEK)
                dateString = "${year}. ${month+1}. ${dayOfMonth} ${dayOfWeekToString(dayOfWeek)}"
                binding.dateButton.text = dateString
            }
            DatePickerDialog(this, dateSetListener, cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH)).show()
        }
        // 버튼 클릭시 timepicker 동작
        binding.timeButton.setOnClickListener {
            var cal = Calendar.getInstance()
            var timeSetListener = TimePickerDialog.OnTimeSetListener { view, hourOfDay, minute ->
                timeString = "${hourOfDay}:${minute}"
                binding.timeButton.text = timeString
            }
            var TimeDialog = TimePickerDialog(this, android.R.style.Theme_Holo_Light_Dialog_NoActionBar, timeSetListener, cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE), true)
            // spinner의 백그라운드 투명하게
            TimeDialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
            TimeDialog.show()
        }


        // 이제 여기서 정보를 다 종합해서 schedule 객체를 채운 후 파이어베이스와 room을 통햏 저장장
    }
   fun dayOfWeekToString(dayOfWeek: Int): String {
        var dayOfWeekString = ""
        when (dayOfWeek) {
            1 -> dayOfWeekString = "일요일"
            2 -> dayOfWeekString = "월요일"
            3 -> dayOfWeekString = "화요일"
            4 -> dayOfWeekString = "수요일"
            5 -> dayOfWeekString = "목요일"
            6 -> dayOfWeekString = "금요일"
            7 -> dayOfWeekString = "토요일"
        }
        return dayOfWeekString
    }
}