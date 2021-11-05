package com.example.jobis.data.repository

import android.util.Log
import com.example.jobis.data.model.Post
import com.example.jobis.data.response.PostResponse
import com.example.jobis.data.response.PostResponseList
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import kotlinx.coroutines.tasks.await

class CommunityRepository {
    suspend fun loadAllPosts(): PostResponseList {
        var postList = PostResponseList()
        return try {
            var db = FirebaseFirestore.getInstance()
            db.collection("posts")
                .get()
                .addOnSuccessListener { result ->
                    for (document in result) {
                        postList.add(PostResponse.from(document.data, document.id))
                    }
                }
                .addOnFailureListener { exception ->
                    Log.d("test", "${exception}")
                }.await()
            postList
        } catch(e: Throwable) {
            e.printStackTrace()
            postList
        }
    }

    suspend fun loadRecentPosts(): PostResponseList {
        var postList = PostResponseList()
        return try {
            val db = FirebaseFirestore.getInstance()
            db.collection("posts")
                .orderBy("created_at", Query.Direction.DESCENDING)
                .get()
                .addOnSuccessListener { result ->
                    for (document in result) {
                        document.data
                        postList.add(PostResponse.from(document.data, document.id))
                    }
                }
                .addOnFailureListener { exception ->
                    Log.d("test", "${exception}")
                }.await()
            postList
        } catch(e: Throwable) {
            e.printStackTrace()
            postList
        }
    }

    suspend fun loadPopularPosts(): PostResponseList {
        var postList = PostResponseList()
        return try {
            val db = FirebaseFirestore.getInstance()
            db.collection("posts")
                .orderBy("like", Query.Direction.DESCENDING)
                .get()
                .addOnSuccessListener { result ->
                    for (document in result) {
                        postList.add(PostResponse.from(document.data, document.id))
                    }
                }
                .addOnFailureListener { exception ->
                    Log.d("test", "${exception}")
                }.await()
            postList
        } catch(e: Throwable) {
            e.printStackTrace()
            postList
        }
    }

    suspend fun loadPostDetail(id: String): PostResponse? {
        var post: PostResponse? = null
        return try {
            val db = FirebaseFirestore.getInstance()
            db.collection("posts").document(id)
                .get()
                .addOnSuccessListener { result ->
                    post = result.data?.let { PostResponse.from(it) }
                }
                .await()
            post
        } catch(e: Throwable) {
            e.printStackTrace()
            post
        }
    }
}