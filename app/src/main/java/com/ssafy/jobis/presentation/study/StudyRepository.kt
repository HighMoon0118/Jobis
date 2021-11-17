package com.ssafy.jobis.presentation.study

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import com.ssafy.jobis.data.model.study.Study
import com.ssafy.jobis.data.model.study.StudyDatabase

class StudyRepository(application: Application) {

    private val db = StudyDatabase.getInstance(application)
    private val dao = db?.getStudyDao()

    fun getAllStudy(): LiveData<List<Study>>? {
        return dao?.getAllStudy()
    }
}