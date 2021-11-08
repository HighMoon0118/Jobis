package com.ssafy.jobis.data.model

data class User(
    val article_list: MutableList<Int> = mutableListOf(),
    val email: String,
    val password: String,
    val nickname: String? = null,
    val comment_list: MutableList<Int> = mutableListOf(),
    val study_list: MutableList<Int> = mutableListOf(),
    val schedule_list: MutableList<Int> = mutableListOf(),
)
