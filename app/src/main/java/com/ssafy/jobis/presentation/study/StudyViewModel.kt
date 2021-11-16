package com.ssafy.jobis.presentation.study

import android.app.Application
import androidx.lifecycle.*
import com.ssafy.jobis.data.model.study.Study
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class StudyViewModel(application: Application): AndroidViewModel(application) {

    private lateinit var _studyList: LiveData<List<Study>>
    val studyList: LiveData<List<Study>> get() = _studyList
    val repo = StudyRepository(application)

    init {
        _studyList = repo.getAllStudy()!!
    }

}