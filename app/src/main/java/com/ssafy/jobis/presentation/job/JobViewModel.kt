package com.ssafy.jobis.presentation.job

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ssafy.jobis.data.model.calendar.CalendarDatabase
import com.ssafy.jobis.data.model.calendar.Schedule
import com.ssafy.jobis.data.repository.JobRepository
import com.ssafy.jobis.data.response.JobResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class JobViewModel(private val jobRepository: JobRepository): ViewModel() {

    private val _jobList = MutableLiveData<MutableList<JobResponse>>()
    val jobList: LiveData<MutableList<JobResponse>> = _jobList
    private val _myJobList = MutableLiveData<List<Schedule>>()
    val myJobList: LiveData<List<Schedule>> = _myJobList
    private val _scheduleResult = MutableLiveData<Boolean>()
    val scheduleResult: LiveData<Boolean> = _scheduleResult


    fun loadJobList() {
        CoroutineScope(Dispatchers.Main).launch {
            val res = jobRepository.loadJobList()
            _jobList.value = res
            Log.d("test", "${res}")
        }
    }

    fun insertSchedule(schedule: Schedule, context: Context) {
        CoroutineScope(Dispatchers.IO).launch {
            val res = jobRepository.insertSchedule(schedule, context)
            _scheduleResult.postValue(res)
        }
    }

    fun loadMyJobList(context: Context) {
        CoroutineScope(Dispatchers.IO).launch {
            val res = jobRepository.loadMyJobList(context)
            if (res is List<Schedule>) {
                _myJobList.postValue(res!!)
            }
        }
    }
}