package com.ssafy.jobis.data.model.study

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

}