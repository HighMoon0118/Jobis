package com.ssafy.jobis.presentation.study

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import com.ssafy.jobis.data.model.study.Study
import com.ssafy.jobis.data.model.study.StudyDatabase

class StudyRepository(context: Context) {

    private val db = StudyDatabase.getInstance(context)
    private val dao = db?.getStudyDao()

    fun getAllStudy(): LiveData<List<Study>>? {
        return dao?.getAllStudy()
    }
}