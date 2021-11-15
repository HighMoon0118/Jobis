package com.ssafy.jobis.presentation.job

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.ssafy.jobis.data.model.calendar.CalendarDatabase
import com.ssafy.jobis.data.model.calendar.Schedule
import com.ssafy.jobis.data.response.JobResponse
import com.ssafy.jobis.databinding.FragmentJobBinding
import com.ssafy.jobis.presentation.MainActivity
import com.ssafy.jobis.presentation.calendar.AlarmReceiver
import kotlinx.android.synthetic.main.fragment_single_schedule.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*

class JobFragment: Fragment() {

    private lateinit var jobViewModel: JobViewModel
    private var _binding: FragmentJobBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentJobBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        jobViewModel = ViewModelProvider(this, JobViewModelFactory())
            .get(JobViewModel::class.java)
        jobViewModel.jobList.observe(viewLifecycleOwner,
            Observer { jobList ->
                jobList ?:return@Observer
                updateJobList(jobList)
            })
        jobViewModel.scheduleResult.observe(viewLifecycleOwner,
            Observer { scheduleResult ->
                scheduleResult ?:return@Observer
                if (scheduleResult) {
                    Toast.makeText(context, "공고가 달력에 등록되었습니다.", Toast.LENGTH_LONG).show()
                } else {
                    Toast.makeText(context, "등록 실패.", Toast.LENGTH_LONG).show()
                }
            })
        jobViewModel.loadJobList()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun updateJobList(jobList: MutableList<JobResponse>) {
        Log.d("test", "jobList: ${jobList}")
        val adapter = JobAdapter()
        adapter.listData = jobList
        adapter.setOnItemClickListener(object: JobAdapter.OnItemClickListener{
            // 각 채용공고 클릭 시 동작하는 함수
            // 여기서 알림설정 코드 작성
            var alarm_id : Int = 0
            override fun onItemClick(v: View, job: JobResponse, post: Int) {
                var newSchedule = Schedule(
                    title=job.title,
                    content=job.content,
                    year=job.year.toInt(),
                    month=job.month.toInt()-1,
                    day=job.day.toInt(),
                    start_time = "",
                    end_time = job.end_time,
                    studyId = 0,
                    groupId = 0,
                    companyName = job.companyName
                )
                var db = CalendarDatabase.getInstance(context)
                CoroutineScope(Dispatchers.IO).launch {
                    alarm_id = db?.calendarDao()?.insert(newSchedule)!!
                }
                
                // 여기에서 알림설정 코드 작성해주면 될듯
                setAlarm(newSchedule, alarm_id)
                
            }
        })
        binding.jobRecyclerView.adapter = adapter
        binding.jobRecyclerView.layoutManager = StaggeredGridLayoutManager(
            2,
            StaggeredGridLayoutManager.VERTICAL
        )
    }

    private fun setAlarm(schedule: Schedule, alarm_id: Int) {
        var alarmCalendar = Calendar.getInstance()
        var time = schedule.end_time.split(":")
        var hour_of_day = time[0].toInt()
        var minute = time[1].toInt()
        alarmCalendar.set(Calendar.YEAR, schedule.year)
        alarmCalendar.set(Calendar.MONTH, schedule.month)
        alarmCalendar.set(Calendar.DAY_OF_MONTH, schedule.day)
        alarmCalendar.set(Calendar.HOUR_OF_DAY, hour_of_day)
        alarmCalendar.set(Calendar.MINUTE, minute)
        alarmCalendar.set(Calendar.SECOND, 0)

        val alarmManager = context?.getSystemService(Context.ALARM_SERVICE) as AlarmManager

        val intent = Intent(this.context, AlarmReceiver::class.java)  // 1
        var pendingIntent = PendingIntent.getBroadcast(this.context, alarm_id, intent, PendingIntent.FLAG_CANCEL_CURRENT)

        if (Build.VERSION.SDK_INT >= 23) {
            alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, alarmCalendar.timeInMillis, pendingIntent)
        }
        else {
            alarmManager.setExact(AlarmManager.RTC_WAKEUP, alarmCalendar.timeInMillis, pendingIntent)
        }
        Toast.makeText(this.context, "알람이 설정되었습니다. ${schedule.id}", Toast.LENGTH_SHORT).show()
    }
    private fun cancelAlarm(schedule: Schedule, alarm_id: Int) {
        val alarmManager = ContextCompat.getSystemService(
            requireContext(),
            AlarmManager::class.java
        ) as AlarmManager
        val intent = Intent(this.context, AlarmReceiver::class.java)
        var pendingIntent = PendingIntent.getBroadcast(this.context, alarm_id, intent, PendingIntent.FLAG_CANCEL_CURRENT)
        alarmManager.cancel(pendingIntent) // 알람 취소
        pendingIntent.cancel() // pendingIntent 취소

        Toast.makeText(this.context, "Alaram Cancelled", Toast.LENGTH_LONG).show()
    }

}