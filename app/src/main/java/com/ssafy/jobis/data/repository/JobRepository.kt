package com.ssafy.jobis.data.repository

import android.content.Context
import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.ssafy.jobis.data.model.calendar.CalendarDatabase
import com.ssafy.jobis.data.model.calendar.Schedule
import com.ssafy.jobis.data.response.JobResponse
import com.ssafy.jobis.presentation.MainActivity
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
        try {
            db?.calendarDao()?.insert(schedule)
            var dbList = db!!.calendarDao().getAll()
            Log.d("test", "jskdlf ${dbList}")
            return true
        } catch(e: Throwable) {
            Log.d("test", "sdklfjl")
            return false
        }
    }
}