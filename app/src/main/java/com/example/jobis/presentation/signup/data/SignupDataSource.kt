package com.example.jobis.presentation.signup.data

import android.text.BoringLayout
import android.util.Log
import com.example.jobis.presentation.signup.data.model.LoggedInUser
import com.example.jobis.presentation.signup.data.model.User
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.*
import kotlinx.coroutines.tasks.await
import java.io.IOException

/**
 * Class that handles authentication w/ login credentials and retrieves user information.
 */
class SignupDataSource {
    suspend fun saveAccount(username: String, nickname: String, password: String): Result<String>? {
        return try {
            var result: Result<String>? = null
            var firestore = FirebaseFirestore.getInstance()
            var user = User(email = username, password = password, nickname = nickname)
            firestore.collection("users")
                .add(user)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        result = Result.Success("success")
                    } else {
                        result = Result.Error(IOException("Error logging in"))
                    }
                }.await()
//                .addOnSuccessListener { documentReference ->
//                    Log.d("test", "db sucess ${documentReference}")
//                    result = Result.Success("success")
//                }
//                .addOnFailureListener { e ->
//                    Log.w("test", "db error")
//                    e.printStackTrace()
//                    result = Result.Error(IOException("Error logging in", e))
//                }.await()
            result
        } catch (e: Throwable) {
            Result.Error(IOException("Error logging in", e))
        }
    }
    suspend fun signup(username: String, password: String): Boolean {
        return try {
            // TODO: handle loggedInUser authentication
            var result: Boolean = false
            var auth = Firebase.auth
            // 1. 계정생성 -> db에 사용자 문서 생성 요청(이걸 백에서 해라..?)
            auth.createUserWithEmailAndPassword(username, password)
                .addOnCompleteListener() {task ->
                    if (task.isSuccessful) {
                        Log.d("test", "계정 생성됨!")
                        result = true
                    } else {
                        result = false
                    }
                }
                .await()
            result
        } catch (e: Throwable) {
            Log.d("test", "계정 생성 요청이 안됨")
            false
        }
    }

    fun logout() {
        // TODO: revoke authentication
    }
}