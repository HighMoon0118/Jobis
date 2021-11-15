package com.ssafy.jobis.data.model.calendar

import androidx.room.*
import java.util.*

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