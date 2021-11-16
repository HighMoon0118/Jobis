package com.ssafy.jobis.data.model.study

import androidx.room.*
import androidx.lifecycle.LiveData


@Dao
interface StudyDao {
    @Insert
    fun insertStudy(study: Study)

    @Insert
    fun insertChat(chat: Chat)

    @Update
    fun updateStudy(study: Study)
    @Update
    fun updateCrew(crew: Crew)

    @Delete
    fun deleteStudy(study: Study)

    @Query("SELECT * FROM Study")
    fun getAllStudy(): LiveData<List<Study>>


    @Transaction
    @Query("SELECT * FROM Study WHERE study_id = :study_id")
    fun getChatList(study_id: String): LiveData<StudyWithChats>
}