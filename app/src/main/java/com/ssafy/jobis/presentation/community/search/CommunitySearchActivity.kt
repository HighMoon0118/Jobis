package com.ssafy.jobis.presentation.community.search

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.ssafy.jobis.R
import com.ssafy.jobis.databinding.CommunitySearchActivityBinding
import com.ssafy.jobis.presentation.community.search.ui.CommunitySearchFragment

class CommunitySearchActivity : AppCompatActivity() {
    private lateinit var binding: CommunitySearchActivityBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = CommunitySearchActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, CommunitySearchFragment.newInstance())
                .commitNow()
        }
    }
}