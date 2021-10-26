package com.example.jobis.presentation.login

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.jobis.R
import com.example.jobis.databinding.ActivityUserBinding
import com.example.jobis.presentation.login.ui.login.LoginFragment
import com.example.jobis.presentation.signup.ui.signup.SignupFragment

class UserActivity : AppCompatActivity() {

    val binding by lazy { ActivityUserBinding.inflate(layoutInflater)}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        val transaction = supportFragmentManager.beginTransaction()
        transaction.add(R.id.userFrame, LoginFragment())
            .commit()
    }

    fun goSignUp() {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.userFrame, SignupFragment())
            .commit()
    }

    fun goLogin() {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.userFrame, LoginFragment())
            .commit()
    }
}