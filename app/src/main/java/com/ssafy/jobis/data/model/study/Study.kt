package com.ssafy.jobis.data.model.study

import androidx.room.*
import androidx.room.ForeignKey.CASCADE
import com.google.gson.Gson

@Entity(tableName = "study")
data class Study(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "study_id")
    var id: Int,
    var title: String,
    var content: String?,
    var location: String?,
    var topic: String,
    var max_user: Int?,
    var current_user: Int = 1,
    var user_list: List<Crew>? = null,
    var created_at: String
)

@Entity(tableName = "crew")
data class Crew(
    @PrimaryKey
    @ColumnInfo(name = "crew_id")
    var id: String,
    )


@Entity(
    tableName = "chat",
    foreignKeys = [
    ForeignKey(
        entity = Study::class,
        parentColumns = ["study_id"],
        childColumns = ["chat_id"],
        onDelete = CASCADE
    )
])

data class Chat(
    @ColumnInfo(name = "chat_id")
    @PrimaryKey(autoGenerate = true) var id: Int,
    var uid: String,
    var content: String,
    var created_at: String,
)

class Converters {
    @TypeConverter
    fun crewListToJson(value: List<Crew>?): String = Gson().toJson(value)

    @TypeConverter
    fun crewJsonToList(value: String) = Gson().fromJson(value, Array<Crew>::class.java).toList()
}

