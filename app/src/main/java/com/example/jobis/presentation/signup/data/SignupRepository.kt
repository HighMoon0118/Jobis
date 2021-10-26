package com.example.jobis.presentation.signup.data

import android.util.Log
import com.example.jobis.R
import com.example.jobis.presentation.signup.data.model.LoggedInUser
import com.example.jobis.presentation.signup.data.model.User
import com.example.jobis.presentation.signup.ui.signup.LoginResult
import com.example.jobis.presentation.signup.ui.signup.SignedUpUserView
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

/**
 * Class that requests authentication and user information from the remote data source and
 * maintains an in-memory cache of login status and user credentials information.
 */

class SignupRepository(val dataSource: LoginDataSource) {

    // in-memory cache of the loggedInUser object
    var user: LoggedInUser? = null
        private set

    val isLoggedIn: Boolean
        get() = user != null

    init {
        // If user credentials will be cached in local storage, it is recommended it be encrypted
        // @see https://developer.android.com/training/articles/keystore
        user = null
    }

    fun logout() {
        user = null
        dataSource.logout()
    }

    suspend fun signup(username: String, password: String): Boolean {
        return dataSource.signup(username, password)
    }

    suspend fun createAccount(username: String, password: String): Result<String>? {
        return dataSource.saveAccount(username, password)
    }

    private fun setLoggedInUser(loggedInUser: LoggedInUser) {
        this.user = loggedInUser
        // If user credentials will be cached in local storage, it is recommended it be encrypted
        // @see https://developer.android.com/training/articles/keystore
    }
}