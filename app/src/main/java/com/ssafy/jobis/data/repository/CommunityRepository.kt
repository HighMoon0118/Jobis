package com.ssafy.jobis.data.repository

import android.util.Log
import com.google.firebase.firestore.FieldValue
import com.ssafy.jobis.data.response.PostResponse
import com.ssafy.jobis.data.response.PostResponseList
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.ssafy.jobis.data.model.community.Comment
import com.ssafy.jobis.presentation.login.Jobis
import kotlinx.coroutines.tasks.await
import java.security.Timestamp

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

    fun updateLike(isLiked: Boolean, post_id: String, uid: String) {
        val db = FirebaseFirestore.getInstance()
        val docRef = db.collection("posts").document(post_id)
        if (isLiked) {
            docRef.update("like", FieldValue.arrayRemove(uid))
        } else {
            docRef.update("like", FieldValue.arrayUnion(uid))
        }
    }

    suspend fun createComment(text: String, post_id: String, uid: String): Boolean? {
        var commentResponse = false
        val db = FirebaseFirestore.getInstance()
        val docRef = db.collection("posts").document(post_id)
        val comment = Comment(
            user_nickname= Jobis.prefs.getString("nickname", "???"),
            user_id=uid,
            post_id=post_id,
            content=text
        )
        return try {
            docRef.update("comment_list", FieldValue.arrayUnion(comment))
                .addOnSuccessListener {
                    commentResponse = true
                }
                .addOnFailureListener { exception ->
                    Log.d("test", "${exception}")
                }
                .await()
            commentResponse
        } catch(e: Throwable) {
            e.printStackTrace()
            commentResponse
        }
    }
}