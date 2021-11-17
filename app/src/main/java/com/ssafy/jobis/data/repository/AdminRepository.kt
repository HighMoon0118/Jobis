package com.ssafy.jobis.data.repository

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import com.ssafy.jobis.data.response.ReportResponse
import com.ssafy.jobis.data.response.UserResponse
import kotlinx.coroutines.tasks.await

class AdminRepository {
    var db = FirebaseFirestore.getInstance()

    suspend fun loadAllUsers(): MutableList<UserResponse> {
        var userList = mutableListOf<UserResponse>()
        return try {
            db.collection("users")
                .get()
                .addOnSuccessListener { result ->
                    for (document in result) {
                        userList.add(UserResponse.from(document.data, document.id))
                    }
                }
                .addOnFailureListener { exception ->
                    Log.d("test", "${exception}")
                }
                .await()
            userList
        } catch (e: Throwable) {
            e.printStackTrace()
            userList
        }
    }

    suspend fun loadAllReports(): MutableList<ReportResponse> {
        val reportList = mutableListOf<ReportResponse>()
        return try {
            db.collection("reports")
                .get()
                .addOnSuccessListener { result ->
                    for (document in result) {
                        reportList.add(ReportResponse.from(document.data, document.id))
                    }
                }
                .addOnFailureListener { exception ->
                    Log.d("test", "${exception}")
                }
                .await()
            reportList
        } catch (e: Throwable) {
            e.printStackTrace()
            reportList
        }
    }

    suspend fun deleteUser(user: UserResponse) {

    }
}