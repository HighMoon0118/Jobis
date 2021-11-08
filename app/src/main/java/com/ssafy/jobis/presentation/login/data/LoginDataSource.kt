package com.ssafy.jobis.presentation.login.data

import com.ssafy.jobis.presentation.login.data.model.LoggedInUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await
import java.io.IOException

/**
 * Class that handles authentication w/ login credentials and retrieves user information.
 */
class LoginDataSource {

    suspend fun login(username: String, password: String): Result<LoggedInUser>? {
        return try {
            var user: LoggedInUser
            var result: Result<LoggedInUser>? = null
            var auth = Firebase.auth
            auth.signInWithEmailAndPassword(username, password)
                .addOnCompleteListener() { task ->
                    if (task.isSuccessful) {
                        result = Result.Success(LoggedInUser(auth.currentUser?.email, auth.currentUser?.email.toString()))
                    } else {
                        result = Result.Error(IOException("Error logging in"))
                    }
                }
                .addOnFailureListener { e ->
                    result = Result.Error(IOException("Error logging in", e))
                }.await()
            // TODO: handle loggedInUser authentication
            return result
        } catch (e: Throwable) {
            return Result.Error(IOException("Error logging in", e))
        }
    }

    fun logout() {
        // TODO: revoke authentication
    }
}