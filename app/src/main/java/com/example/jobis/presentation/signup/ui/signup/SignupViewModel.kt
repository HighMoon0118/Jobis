package com.example.jobis.presentation.signup.ui.signup

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import android.util.Patterns
import com.example.jobis.R
import com.example.jobis.presentation.signup.data.SignupRepository
import com.example.jobis.presentation.signup.data.Result
import kotlinx.coroutines.*

class SignupViewModel(private val signupRepository: SignupRepository) : ViewModel() {

    private val _loginForm = MutableLiveData<LoginFormState>()
    val loginFormState: LiveData<LoginFormState> = _loginForm

    private val _loginResult = MutableLiveData<LoginResult>()
    val loginResult: LiveData<LoginResult> = _loginResult

    fun login(username: String, nickname: String, password: String) {
        // can be launched in a separate asynchronous job
        CoroutineScope(Dispatchers.Main).launch {
            val job1 = CoroutineScope(Dispatchers.IO).async {
                signupRepository.signup(username, password)
            }
            val job2 = CoroutineScope(Dispatchers.IO).async {
                signupRepository.createAccount(username, nickname, password)
            }
            val a = job1.await()
            if (a) {
                if (job2.await() is Result.Success) {
                    _loginResult.value =
                        LoginResult(success = SignedUpUserView(displayName = "회원가입 성공"))
                } else {
                    _loginResult.value = LoginResult(error = R.string.login_failed)
                }
            } else {
                _loginResult.value = LoginResult(error = R.string.login_failed)
            }

        }
    }

    fun loginDataChanged(username: String, password: String) {
        if (!isUserNameValid(username)) {
            _loginForm.value = LoginFormState(usernameError = R.string.invalid_username)
        } else if (!isPasswordValid(password)) {
            _loginForm.value = LoginFormState(passwordError = R.string.invalid_password)
        } else {
            _loginForm.value = LoginFormState(isDataValid = true)
        }
    }

    // A placeholder username validation check
    private fun isUserNameValid(username: String): Boolean {
        return if (username.contains("@")) {
            Patterns.EMAIL_ADDRESS.matcher(username).matches()
        } else {
            username.isNotBlank()
        }
    }

    // A placeholder password validation check
    private fun isPasswordValid(password: String): Boolean {
        return password.length > 5
    }
}