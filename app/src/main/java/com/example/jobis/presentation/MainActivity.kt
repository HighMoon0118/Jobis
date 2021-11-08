package com.example.jobis.presentation

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import com.example.jobis.R
import com.example.jobis.data.response.PostResponse
import com.example.jobis.databinding.ActivityMainBinding
import com.example.jobis.presentation.calendar.CalendarFragment
import com.example.jobis.presentation.community.CommunityFragment
import com.example.jobis.presentation.community.create.PostCreateFragment
import com.example.jobis.presentation.community.detail.CommunityDetailActivity
import com.example.jobis.presentation.job.JobFragment
import com.example.jobis.presentation.login.UserActivity
import com.example.jobis.presentation.myPage.MyPageFragment
import com.example.jobis.presentation.study.MyStudyFragment

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val getResultCommunityDetail = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == RESULT_OK) {
            goCommunityFragment()
        }
    }
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
                        supportFragmentManager.beginTransaction().replace(R.id.frame_main, MyStudyFragment()).commit()
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

    fun goUserActivity() {
        val intent = Intent(this, UserActivity::class.java)
        startActivity(intent)
        finish()
    }

    fun goPostCreateFragment() {
        supportFragmentManager.beginTransaction()
            .add(R.id.frame_main, PostCreateFragment())
            .addToBackStack(null)
            .commit()
    }

    fun goCommunityFragment() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.frame_main, CommunityFragment())
            .commit()
    }

    fun goCommunityDetailActivity(post_id: String) {
        val intent = Intent(this, CommunityDetailActivity::class.java)
        intent.putExtra("id", post_id)
        getResultCommunityDetail.launch(intent)
    }
}