package com.ssafy.jobis.data.response

data class ScheduleResponse(
    val companyName: String,
    val content: String,
    val year: Long,
    val month: Long,
    val day: Long,
    val start_time: String,
    val end_time: String,
    val groupId: Long,
    val studyId: Long,
    val title: String,
) {
    companion object {
        fun from(map: HashMap<String, Any>): ScheduleResponse {
            return object {
                val companyName by map
                val content by map
                val year by map
                val month by map
                val day by map
                val start_time by map
                val end_time by map
                val groupId by map
                val studyId by map
                val title by map
                val data = ScheduleResponse(
                    companyName as String,
                    content as String,
                    year as Long,
                    month as Long,
                    day as Long,
                    start_time as String,
                    end_time as String,
                    groupId as Long,
                    studyId as Long,
                    title as String
                )
            }.data
        }
    }
}