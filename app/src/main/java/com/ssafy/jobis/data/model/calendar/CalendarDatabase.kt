package com.ssafy.jobis.data.model.calendar

import android.content.Context
import androidx.room.Dao
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


@Database(entities = [Schedule::class], version = 2)
abstract class CalendarDatabase : RoomDatabase() {
    abstract fun calendarDao() : CalendarDao

    companion object {
        private var instance: CalendarDatabase? = null

        @Synchronized
        fun getInstance(context: Context?): CalendarDatabase? {
            if (instance == null) {
                synchronized(CalendarDatabase::class) {
                    instance = Room.databaseBuilder(
                        context!!.applicationContext,
                        CalendarDatabase::class.java,
                        "calendar-database"
                    ).build()
                }
            }
            return instance
        }
    }
}
