package com.example.jobis.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.jobis.R
import com.example.jobis.databinding.ActivityMainBinding
import com.example.jobis.presentation.calendar.CalendarFragment
import com.example.jobis.presentation.community.CommunityFragment
import com.example.jobis.presentation.job.JobFragment
import com.example.jobis.presentation.myPage.MyPageFragment
import com.example.jobis.presentation.study.StudyFragment

class MainActivity : AppCompatActivity() {


    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportFragmentManager.beginTransaction().add(R.id.frame_main, CalendarFragment()).commit()

        binding.btmNavi.run {
            setOnNavigationItemSelectedListener {
                when(it.itemId) {
                    R.id.item_calendar -> {
                        supportFragmentManager.beginTransaction().replace(R.id.frame_main, CalendarFragment()).commit()
                    }
                    R.id.item_study -> {
                        supportFragmentManager.beginTransaction().replace(R.id.frame_main, StudyFragment()).commit()
                    }
                    R.id.item_job -> {
                        supportFragmentManager.beginTransaction().replace(R.id.frame_main, JobFragment()).commit()
                    }
                    R.id.item_community -> {
                        supportFragmentManager.beginTransaction().replace(R.id.frame_main, CommunityFragment()).commit()
                    }
                    R.id.item_my_page -> {
                        supportFragmentManager.beginTransaction().replace(R.id.frame_main, MyPageFragment()).commit()
                    }
                }
                true
            }
        }
    }
}