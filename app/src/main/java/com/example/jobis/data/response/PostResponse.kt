package com.example.jobis.data.response

import com.google.firebase.Timestamp

data class PostResponse(

    val title: String,
    val content: String,
    val created_at: Timestamp,
    val updated_at: Timestamp,
    val comment_list: MutableList<Any>,
    val user_id: String,
    val like: MutableList<Any>,
    val id: String? = null
) {
    companion object {
        fun from(map: Map<String, Any>, post_id: String? = null) = object {
            val title by map
            val content by map
            val created_at by map
            val updated_at by map
            val comment_list by map
            val user_id by map
            val like by map
            val data = PostResponse(
                title as String,
                content as String,
                created_at as Timestamp,
                updated_at as Timestamp,
                comment_list as MutableList<Any>,
                user_id as String,
                like as MutableList<Any>,
                id=post_id
            )
        }.data
    }
}
