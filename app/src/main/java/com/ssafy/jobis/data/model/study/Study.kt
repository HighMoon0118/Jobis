package com.ssafy.jobis.data.model.study

import androidx.room.*
import androidx.room.ForeignKey.CASCADE
import com.google.firebase.database.Exclude
import com.google.gson.Gson

@Entity(tableName = "Study")
data class Study(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "study_id")
    var id: String = "",
    var title: String = "",
    var content: String? = "",
    var location: String? = "",
    var topic: String? = "",
    var max_user: Int? = 0,
    var current_user: Int? = 1,
    var user_list: MutableList<Crew>? = null,
    var created_at: String = "",
    var unread_chat_cnt : Int? = 0

){
    @Exclude
    fun toMap(): Map<String, Any?> {
        return mapOf(
            "id" to id,
            "title" to title,
            "content" to content,
            "location" to location,
            "topic" to topic,
            "max_user" to max_user,
            "current_user" to current_user,
            "user_list" to user_list,
            "created_at" to created_at,
            "unread_chat_cnt" to unread_chat_cnt
        )
    }
}

@Entity(tableName = "crew")
data class Crew(
    @PrimaryKey
    @ColumnInfo(name = "crew_id")
    var id: String = "",
    )

@Entity(
    tableName = "chat",
    foreignKeys = [
        ForeignKey(
            entity = Study::class,
            parentColumns = ["study_id"],
            childColumns = ["study_id"],
            onDelete = CASCADE
        )
    ]
)
data class Chat(
    @ColumnInfo(name = "chat_id")
    @PrimaryKey(autoGenerate = true)
    var chat_id: Int = 0,
    var user_id: String,
    var content: String,
    var url: String? = null,
    var created_at: String,
    @ColumnInfo(name = "study_id") var study_id: String,
)

class Converters {
    @TypeConverter
    fun crewListToJson(value: List<Crew>?): String = Gson().toJson(value)

    @TypeConverter
    fun crewJsonToList(value: String) = Gson().fromJson(value, Array<Crew>::class.java).toList()
}

