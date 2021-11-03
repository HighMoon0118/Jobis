package com.example.jobis.data.repository

import android.util.Log
import com.example.jobis.data.model.Post
import com.example.jobis.presentation.community.PostList
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class CommunityRepository {
    suspend fun loadAllPosts(): PostList {
        var postList = PostList()
        return try {
            var db = FirebaseFirestore.getInstance()
            db.collection("posts")
                .get()
                .addOnSuccessListener { result ->
                    for (document in result) {
                        postList.add(Post.from(document.data))
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
}