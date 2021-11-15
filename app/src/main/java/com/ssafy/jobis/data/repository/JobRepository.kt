package com.ssafy.jobis.data.repository

import android.content.Context
import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.ssafy.jobis.data.model.calendar.CalendarDatabase
import com.ssafy.jobis.data.model.calendar.Schedule
import com.ssafy.jobis.data.response.JobResponse
import kotlinx.coroutines.tasks.await

class JobRepository {
    suspend fun loadJobList(): MutableList<JobResponse> {
        val jobList = mutableListOf<JobResponse>()
        return try {
            var db = FirebaseFirestore.getInstance()
            db.collection("schedules")
                .get()
                .addOnSuccessListener { result ->
                    for (document in result) {
                        jobList.add(JobResponse.from(document.data as HashMap<String, Any>))
                    }
                }
                .addOnFailureListener { exception ->
                    Log.d("test", "${exception}")
                }
                .await()
            jobList
        } catch(e: Throwable) {
            e.printStackTrace()
            jobList
        }
    }

    fun insertSchedule(schedule: Schedule, context: Context): Boolean {
        var db = CalendarDatabase.getInstance(context)
        val content = schedule.content
        val list = db?.calendarDao()?.getAll()
        Log.d("test", "${list}")
        val isAdded = db?.calendarDao()?.getSchedule(content)?.size!!
        if (isAdded > 0) {
            return false
        }
        db?.calendarDao()?.insert(schedule)
        return true
    }

    fun loadMyJobList(context: Context): List<Schedule>? {
        var db = CalendarDatabase.getInstance(context)
        return db?.calendarDao()?.getMyJob()
    }
}