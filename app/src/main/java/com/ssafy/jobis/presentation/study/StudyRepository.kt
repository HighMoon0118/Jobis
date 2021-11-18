package com.ssafy.jobis.presentation.study

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import com.ssafy.jobis.data.model.study.Study
import com.ssafy.jobis.data.model.study.StudyDatabase
import kotlinx.coroutines.CoroutineScope

class StudyRepository(context: Context) {

    private val db = StudyDatabase.getInstance(context)
    private val dao = db?.getStudyDao()

    fun getAllStudy(): LiveData<List<Study>>? {
        return dao?.getAllStudy()
    }

    fun addUnreadChat(studyId: String) {
        if (dao == null) return
        val study = dao.getStudy(studyId)
        study.unread_chat_cnt ++
        dao.updateStudy(study)
    }
}