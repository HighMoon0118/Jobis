package com.ssafy.jobis.presentation

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.ssafy.jobis.R
import com.ssafy.jobis.databinding.ActivityMainBinding
import com.ssafy.jobis.presentation.calendar.CalendarFragment
import com.ssafy.jobis.presentation.community.CommunityFragment
import com.ssafy.jobis.presentation.community.create.PostCreateFragment
import com.ssafy.jobis.presentation.community.detail.CommunityDetailActivity
import com.ssafy.jobis.presentation.job.JobFragment
import com.ssafy.jobis.presentation.login.UserActivity
import com.ssafy.jobis.presentation.myPage.MyPageFragment
import com.ssafy.jobis.presentation.study.MyStudyFragment

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

        requestPermissions()

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

    private fun requestPermissions() {

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

            val permissions: Array<String> = arrayOf( Manifest.permission.READ_EXTERNAL_STORAGE)
            ActivityCompat.requestPermissions(this, permissions, 0)
        }
    }


    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode==0) {
            if (grantResults.isNotEmpty()) {
                var isAllGranted = true
                for (grant in grantResults) {
                    if (grant != PackageManager.PERMISSION_GRANTED) {
                        isAllGranted = false
                        break;
                    }
                }
                Log.d("ㅇㅇㅇㅇ", isAllGranted.toString())
                if (isAllGranted) {

                } else {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                        AlertDialog
                            .Builder(this)
                            .setTitle("권한 설정")
                            .setMessage("미디어 액세스를 허용해주세요")
                            .setPositiveButton("권한 설정하러 가기"){ dialog, which ->
                                val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                                    .setData(Uri.parse("package:$packageName"))
                                startActivity(intent);
                            }
                            .setNegativeButton("취소"){ dialog, which ->
                                finish()
                            }
                            .create()
                            .show()
                    }
                }
            }
        }
    }
}