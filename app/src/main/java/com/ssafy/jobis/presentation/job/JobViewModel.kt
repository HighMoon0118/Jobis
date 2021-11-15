package com.ssafy.jobis.presentation.job

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ssafy.jobis.data.repository.JobRepository
import com.ssafy.jobis.data.response.JobResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class JobViewModel(private val jobRepository: JobRepository): ViewModel() {

    private val _jobList = MutableLiveData<JobResponse>()
    val jobList: LiveData<JobResponse> = _jobList

    fun loadJobList() {
        CoroutineScope(Dispatchers.Main).launch {
            val res = jobRepository.loadJobList()
            _jobList.value = res
        }
    }
}