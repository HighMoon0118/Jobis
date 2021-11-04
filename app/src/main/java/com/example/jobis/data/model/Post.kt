package com.example.jobis.data.model

import com.google.firebase.Timestamp
import java.util.Date

data class Post(
    val title: String,
    val content: String,
    val created_at: Timestamp = Timestamp(Date()),
    val updated_at: Timestamp = Timestamp(Date()),
    val comment_list: MutableList<Any> = mutableListOf(),
    val user_id: String,
    val like: MutableList<Any> = mutableListOf()
) {
    companion object {
        fun from(map: Map<String, Any>) = object {
            val title by map
            val content by map
            val created_at by map
            val updated_at by map
            val comment_list by map
            val user_id by map
            val like by map
            val data = Post(title as String,
                content as String,
                created_at as Timestamp,
                updated_at as Timestamp,
                comment_list as MutableList<Any>,
                user_id as String,
                like as MutableList<Any>
            )
        }.data
    }
}

