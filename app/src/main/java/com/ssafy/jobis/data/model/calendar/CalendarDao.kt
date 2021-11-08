package com.ssafy.jobis.data.model.calendar

import androidx.room.*

@Dao
interface CalendarDao {
    @Query("SELECT * FROM schedule")
    fun getAll(): List<Schedule>

    @Insert
    fun insert(schedule: Schedule)

    @Update
    fun update(schedule: Schedule)

    @Delete
    fun delete(schedule: Schedule)
}