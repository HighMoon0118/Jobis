package com.example.jobis.data.repository

import com.example.jobis.presentation.community.PostList

class PostMainRepository {
    suspend fun loadAllPosts(): PostList {
        return PostList()
    }
}