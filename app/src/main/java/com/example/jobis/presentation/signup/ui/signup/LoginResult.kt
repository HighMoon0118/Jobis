package com.example.jobis.presentation.signup.ui.signup

/**
 * Authentication result : success (user details) or error message.
 */
data class LoginResult(
    val success: SignedUpUserView? = null,
    val error: Int? = null
)