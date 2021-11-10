package com.ssafy.jobis.data.model.calendar

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Schedule (
    @ColumnInfo var title: String,
    @ColumnInfo var content: String,
    @ColumnInfo var year: Int,
    @ColumnInfo var month: Int,
    @ColumnInfo var day: Int,
    @ColumnInfo var startTime: String,
    @ColumnInfo var endTime: String,
    ) {
    @PrimaryKey(autoGenerate = true) var id: Int = 0
}


//@Entity
//data class Schedule (
//    var title: String,
//    var content: String,
//    var date: String
//) {
//    @PrimaryKey(autoGenerate = true) var id:Int = 0
//}