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
    val like: MutableList<Any> = mutableListOf(),
)

