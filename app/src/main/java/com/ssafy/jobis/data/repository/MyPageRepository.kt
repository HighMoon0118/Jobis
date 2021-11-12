package com.ssafy.jobis.data.repository

import android.content.ContentValues
import android.content.ContentValues.TAG
import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.ssafy.jobis.data.model.community.Comment
import com.ssafy.jobis.data.response.PostResponse
import com.ssafy.jobis.data.response.PostResponseList
import kotlinx.coroutines.tasks.await
import java.util.HashMap

class MyPageRepository {
    // 룸에 데이터 저장해야 될 것 같다
    suspend fun loadMyLikePosts(uid: String): PostResponseList {
        // 1. likelist 가져오기
        // 2. post_id만큼 문서 가져오기
        val db = FirebaseFirestore.getInstance()
        val postList = PostResponseList()
        val userRef = db.collection("users").document(uid)
        val postRef = db.collection("posts")
        return try {
            db.runTransaction { transaction ->
                val snapshot = transaction.get(userRef)
                val likeList = snapshot.get("like_post_list")
                for (post_id in likeList as List<*>) {
                    val query = postRef.document(post_id as String)
                    val snapshot = transaction.get(query)
                    snapshot.data?.let { PostResponse.from(it, post_id) }?.let { postList.add(it) }
                }
            }
                .await()
            postList
        } catch(e: Throwable) {
            e.printStackTrace()
            postList
        }
    }
    suspend fun loadMyPosts(uid: String): PostResponseList {
        // 트랜잭션 처리
        // 1. 내 postlist 가져오기
        // 2. 내 post_id로 문서 다 가져오기
        val db = FirebaseFirestore.getInstance()
        val postList = PostResponseList()
        val userRef = db.collection("users").document(uid)
        val postRef = db.collection("posts")
        return try {
            db.runTransaction { transaction ->
                val snapshot = transaction.get(userRef)
                val articleList = snapshot.get("article_list")
                for (post_id in articleList as List<*>) {
                    val query = postRef.document(post_id as String)
                    val snapshot = transaction.get(query)
                    snapshot.data?.let { PostResponse.from(it, post_id) }?.let { postList.add(it) }
                }
            }
                .await()
            postList
        } catch(e: Throwable) {
            e.printStackTrace()
            postList
        }
    }
    suspend fun loadMyCommentPosts(uid: String): MutableList<Comment> {
        // 1. 내 comment 리스트 가져오기
        val db = FirebaseFirestore.getInstance()
        val commentList = mutableListOf<Comment>()
        val userRef = db.collection("users").document(uid)
        return try {
            userRef.get()
                .addOnSuccessListener { result ->
                    val comments = result.get("comment_list")
                    for (item in comments as List<*>) {
                        commentList.add(Comment.from(item as HashMap<String, Any>))
                    }
                }
                .addOnFailureListener { e ->
                    Log.w(TAG, "${e}")
                }
                .await()
            commentList
        } catch(e: Throwable) {
            e.printStackTrace()
            commentList
        }
    }
}