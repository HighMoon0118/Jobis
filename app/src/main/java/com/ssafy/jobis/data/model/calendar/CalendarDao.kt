package com.ssafy.jobis.data.model.calendar

import androidx.room.*
import androidx.room.OnConflictStrategy.REPLACE
import java.util.*

@Dao
interface CalendarDao {
    @Query("SELECT * FROM schedule")
    fun getAll(): List<Schedule>

    @Insert
<<<<<<< HEAD
    fun insert(schedule: Schedule) : Long
=======
    fun insert(schedule: Schedule): Long
>>>>>>> b9ca7fe0dcb8c2c17a959e65392e7a13dc932345

    @Update
    fun update(schedule: Schedule)

    @Delete
    fun delete(schedule: Schedule)

    @Query("SELECT * FROM schedule WHERE content = :content")
    fun getSchedule(content: String) : List<Schedule>

    @Query("SELECT * FROM schedule WHERE companyName > 0")
    fun getMyJob(): List<Schedule>

    @Query("SELECT * FROM schedule WHERE id = :id")
    fun getJob(id: Int): Schedule
}