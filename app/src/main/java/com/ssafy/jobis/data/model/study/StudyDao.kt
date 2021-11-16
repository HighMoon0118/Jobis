package com.ssafy.jobis.data.model.study

import androidx.lifecycle.LiveData
import androidx.room.*

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

    @Query("SELECT * FROM study")
    fun getAllStudy(): LiveData<List<Study>>


    @Transaction
    @Query("SELECT * FROM study WHERE study_id = :study_id")
    fun getChatList(study_id:Int): LiveData<StudyWithChats>
}