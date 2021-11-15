package com.ssafy.jobis.data.response

data class JobResponse(
    val companyName: String,
    val content: String,
    val year: Int,
    val month: Int,
    val day: Int,
    val end_time: String,
    val groupId: Int,
    val studyId: Int,
    val title: String,
)
