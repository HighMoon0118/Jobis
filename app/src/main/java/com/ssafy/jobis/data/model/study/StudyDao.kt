package com.ssafy.jobis.data.model.study

import androidx.room.*

@Dao
interface StudyDao {
    // 스터디 새로 생성
    @Insert
    fun insertStudy(study: Study)
    
    // 채팅 새로 만듬
    @Insert
    fun insertChat(chat: Chat)

    @Update
    fun updateStudy(study: Study)
    @Update
    fun updateCrew(crew: Crew)

    @Delete
    fun deleteStudy(study: Study)
    
    // 현재 로컬에 저장된 모든 스터디들을 가져오는것
    @Query("SELECT * FROM Study")
    fun getAllStudy(): List<Study>

}