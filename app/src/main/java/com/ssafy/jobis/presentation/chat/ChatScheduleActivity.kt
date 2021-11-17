package com.ssafy.jobis.presentation.chat

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.google.android.gms.dynamic.SupportFragmentWrapper
import com.google.firebase.firestore.FirebaseFirestore
import com.ssafy.jobis.R
import com.ssafy.jobis.data.model.calendar.Schedule
import com.ssafy.jobis.data.response.JobResponse
import com.ssafy.jobis.data.response.ScheduleResponse
import com.ssafy.jobis.databinding.ActivityChatBinding
import com.ssafy.jobis.databinding.ActivityChatCalendarBinding
import com.ssafy.jobis.presentation.chat.adapter.ChatScheduleAdapter
import com.ssafy.jobis.presentation.chat.viewmodel.ChatScheduleViewModel
import com.ssafy.jobis.presentation.chat.viewmodel.ChatScheduleViewModelFactory
import com.ssafy.jobis.presentation.community.search.CommunitySearchActivity
import com.ssafy.jobis.presentation.job.JobAdapter
import com.ssafy.jobis.presentation.job.JobViewModel
import com.ssafy.jobis.presentation.job.JobViewModelFactory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

class ChatScheduleActivity : AppCompatActivity() {
    private lateinit var binding: ActivityChatCalendarBinding
    private lateinit var chatScheduleViewModel: ChatScheduleViewModel
    private val getResultChatScheduleAddActivity = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == RESULT_OK) {
            chatScheduleViewModel.loadScheduleList()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChatCalendarBinding.inflate(layoutInflater)
        setContentView(binding.root)

        chatScheduleViewModel = ViewModelProvider(this, ChatScheduleViewModelFactory())
            .get(ChatScheduleViewModel::class.java)

        chatScheduleViewModel.scheduleList.observe(this, Observer { scheduleList ->
            scheduleList ?:return@Observer
            updateStudyScheduleList(scheduleList)
        })

        chatScheduleViewModel.loadScheduleList()

        binding.addStudySchedule.setOnClickListener {
            var intent = Intent(this, ChatScheduleAddActivity::class.java)
            getResultChatScheduleAddActivity.launch(intent)
        }
    }

//    fun goCommunitySearchActivity() {
//        val intent = Intent(this, CommunitySearchActivity::class.java)
//        getResultCommunityDetail.launch(intent)
//    }



    private fun updateStudyScheduleList(scheduleList: MutableList<ScheduleResponse>) {
        var schedules = ArrayList<Schedule>()
        for (i: Int in 0..scheduleList.size-1) {
            println("scheduleList[${i}]" + scheduleList[i].title)
            println("scheduleList[${i}]" + scheduleList[i].study_id)
            if (scheduleList[i].study_id != 0L) { // studyId가 있는 경우만
                var year = scheduleList[i].year.toInt()
                var month = scheduleList[i].month.toInt()
                var day = scheduleList[i].day.toInt()
                var title = scheduleList[i].title
                var content = scheduleList[i].content
                var groupId = scheduleList[i].group_id.toInt()
                var studyId = scheduleList[i].study_id.toInt()
                var companyName = scheduleList[i].companyName
                var startTime = scheduleList[i].start_time
                var endTime = scheduleList[i].end_time

                var schedule = Schedule(title, content, year, month, day, startTime, endTime, studyId, groupId, companyName)
                schedules.add(schedule)
            }
            var scheduleRecycler = binding.studySchedule
            println("schedules: " + schedules)
            scheduleRecycler.adapter = ChatScheduleAdapter(schedules)
        }
    }

}
