package com.ssafy.jobis.presentation.calendar

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.os.Looper
import android.view.ContextThemeWrapper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.core.graphics.rotationMatrix
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.prolificinteractive.materialcalendarview.CalendarDay
import com.ssafy.jobis.R
import com.ssafy.jobis.data.model.calendar.CalendarDatabase
import com.ssafy.jobis.data.model.calendar.OnDeleteClick
import com.ssafy.jobis.data.model.calendar.RoutineScheduleDatabase
import com.ssafy.jobis.data.model.calendar.Schedule
import com.ssafy.jobis.databinding.ActivityUserBinding
import com.ssafy.jobis.databinding.FragmentCalendarBinding
import com.ssafy.jobis.presentation.CalendarPagerAdapter
import kotlinx.android.synthetic.main.fragment_calendar.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*
import java.util.logging.Handler
import kotlin.collections.ArrayList
import kotlin.concurrent.timer

class CalendarScheduleAdapter(private val datas: ArrayList<Schedule>, private var frag: CalendarFragment) : RecyclerView.Adapter<CalendarScheduleAdapter.ViewHolder>() {
    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private var title: TextView = itemView.findViewById(R.id.schedule_title)
        private var time: TextView = itemView.findViewById(R.id.schedule_time)
        private var content: TextView = itemView.findViewById(R.id.schedule_content)

        fun bind(item: Schedule) {
            var startTime = item.start_time
            var endTime = item.end_time
            title.text = item.title
            if (startTime != "") {
                time.text = "${startTime} - ${endTime}"
            } else {
                time.text = "${endTime} 마감"
            }

            content.text = item.content
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_schedule, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(datas[position])
        val deleteBtn = holder.itemView.findViewById<Button>(R.id.schedule_delete) // 삭제 버튼
        deleteBtn.setOnClickListener {
            println("item확인" + datas[position].groupId)
            // 삭제하시겠습니까? 화면
            val builder = AlertDialog.Builder(ContextThemeWrapper(holder.itemView.context, R.style.ThemeOverlay_AppCompat_Dialog))
            builder.setTitle("일정")
            builder.setMessage("일정을 삭제하시겠습니까?")

            builder.setPositiveButton("확인", DialogInterface.OnClickListener {
                dialog, which -> println("삭제 시작")
                var year = datas[position].year
                var month = datas[position].month
                var day = datas[position].day
                var scheduleData = CalendarDatabase.getInstance(holder.itemView.context)
                var routineScheduleData = RoutineScheduleDatabase.getInstance(holder.itemView.context)
                var calendarDay = CalendarDay.from(year, month, day)
                // room으로 db에서 삭제
                CoroutineScope(Dispatchers.IO).launch {
                    var scheduleList = scheduleData!!.calendarDao().getAll() // 모든 일정 가져오기 [Schedule, Schedule, Schedule, ...]
                    var routineList = routineScheduleData!!.routineScheduleDao().getAll() // 모든 루틴 가져오기
                    for (num in 0..routineList.size-1) {
                        if (routineList[num].id == datas[position].groupId) {
                            routineScheduleData.routineScheduleDao().delete(routineList[num]) // 해당 routineSchedule 객체 삭제
                        }
                    }
                    scheduleData.calendarDao().delete(datas[position])
                }

                android.os.Handler(Looper.getMainLooper()).postDelayed({
                    frag.onMonthChanged(frag.calendar_view, calendarDay)
                    frag.selectedDate(day)
                }, 0)




            })
            builder.setNegativeButton("취소") {
                _, _ -> println("취소 버튼")
            }
            builder.show()

            // yes를 누르면 room과 파이어베이스에서 모두 삭제 후 연동

        }
    }

    override fun getItemCount(): Int {
        return datas.size
    }
}