package com.example.jobis.presentation.community.detail

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.os.bundleOf
import com.example.jobis.R
import com.example.jobis.databinding.CommunityDetailActivityBinding
import com.example.jobis.presentation.community.detail.ui.detail.CommunityDetailFragment

class CommunityDetailActivity : AppCompatActivity() {

    private lateinit var binding: CommunityDetailActivityBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = CommunityDetailActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)
        if (savedInstanceState == null) {
            val fragment = CommunityDetailFragment.newInstance()
            val id = intent.getStringExtra("id")
            fragment.arguments = bundleOf("id" to id)
            supportFragmentManager.beginTransaction()
                .replace(R.id.frameLayout, fragment)
                .commitNow()
        }

        binding.postDetailBackButton.setOnClickListener {
            val returnIntent = Intent()
            setResult(RESULT_OK, returnIntent)
            finish()
        }
    }

    override fun onBackPressed() {
        val returnIntent = Intent()
        setResult(RESULT_OK, returnIntent)
        finish()
    }
}