package com.example.jobis.presentation.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.example.jobis.R
import com.example.jobis.databinding.ActivityUserBinding
import com.example.jobis.presentation.MainActivity
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

    fun goMain() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    fun loadingOff() {
        binding.loading.visibility = View.GONE
    }
    fun loadingOn() {
        Log.d("test", "로딩 온!!")
        binding.loading.visibility = View.VISIBLE
    }
}