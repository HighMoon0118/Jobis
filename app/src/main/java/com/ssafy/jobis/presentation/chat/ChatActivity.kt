package com.ssafy.jobis.presentation.chat

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.GravityCompat
import com.ssafy.jobis.R
import com.ssafy.jobis.databinding.ActivityChatBinding
import com.ssafy.jobis.presentation.chat.adapter.ChatAdapter
//import com.ssafy.jobis.R
//import com.ssafy.jobis.databinding.ActivityChatBinding
//import com.ssafy.jobis.presentation.chat.adapter.ChatAdapter
import com.ssafy.jobis.presentation.chat.adapter.ViewPagerAdapter
import kotlinx.coroutines.*

class ChatActivity: AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityChatBinding
    private val chatAdapter: ChatAdapter by lazy {
        ChatAdapter()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        requestPermissions()

        binding = ActivityChatBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.rvChat.adapter = chatAdapter
        goToRecentChat()

        binding.viewpagerChat.apply {
            isUserInputEnabled = false
            adapter = ViewPagerAdapter(this@ChatActivity)
        }

        setSupportActionBar(binding.tbChat)  // 액션바 설정
        val actionbar = supportActionBar

        actionbar?.apply {
            setDisplayShowTitleEnabled(false)  // 앱 이름 안보이게
            setDisplayHomeAsUpEnabled(true)  // 뒤로가기 버튼
            setHomeAsUpIndicator(R.drawable.ic_arrow_back_24)  // 뒤로가기 아이콘 설정
        }


        binding.apply {
            imgAddChat.setOnClickListener(this@ChatActivity)
            imgEmoticonChat.setOnClickListener(this@ChatActivity)
            imgSendChat.setOnClickListener(this@ChatActivity)
            editTextChat.setOnClickListener(this@ChatActivity)
            imgSelectColor.setOnClickListener(this@ChatActivity)
            imgLeft.setOnClickListener(this@ChatActivity)
            imgRight.setOnClickListener(this@ChatActivity)
        }
    }

    private fun goToRecentChat() {
        binding.rvChat.post {
            binding.rvChat.scrollToPosition(19)
        }
    }

    suspend fun showCanvas(view: View) {
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_NOTHING)   // 키보드가 layout에 영향을 주지 않게 함
        binding.frameEmoticonChat.visibility = View.VISIBLE                                 // 캔버스를 보임
        imm.hideSoftInputFromWindow(view.windowToken, 0)                           // 키보드를 숨김
        delay(100)                                                              // 키보드가 전부 안보일 떄까지 기다림
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)    // 키보드가 layout에 영향을 주도록 함
    }

    suspend fun showKeyboard() {
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_NOTHING)   // 키보드가 layout에 영향을 주지 않게 함
        imm.showSoftInput(binding.editTextChat, 0)                                 // 키보드를 보임
        delay(100)                                                              // 키보드가 전부 보일 떄까지 기다림
        binding.frameEmoticonChat.visibility = View.GONE                                    // 캔버스를 숨김
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)    // 키보드가 layout에 영향을 주도록 함
    }


    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.img_add_chat -> {

            }
            R.id.img_emoticon_chat -> {
                val scope = CoroutineScope(Dispatchers.Main)
                scope.launch {
                    if (binding.frameEmoticonChat.visibility == View.GONE) {     // 1. 키보드가 보일 때
                        showCanvas(view)
                    } else {                                                     // 2. 캔버스가 보일 때
                        showKeyboard()
                    }
                }
            }
            R.id.img_send_chat -> {

            }
            R.id.edit_text_chat -> {
                val scope = CoroutineScope(Job() + Dispatchers.Main)
                scope.launch {
                    val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager

                    if (binding.frameEmoticonChat.visibility == View.GONE) {      // 1. Edit Text만 보일 때
                        imm.showSoftInput(binding.editTextChat, 0)           // 키보드를 보임
                    } else {                                                      // 2. 캔버스가 보일 때
                        showKeyboard()
                    }
                }
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_chat_toolbar, menu)  // 툴바의 메뉴들 연결
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {  // 툴바의 메뉴를 눌럿을 때
        when (item.itemId) {
            android.R.id.home -> onBackPressed()  // 뒤로가기
            R.id.item_open_nav -> binding.dlChat.openDrawer(GravityCompat.END)  // 네비게이션바 오픈. END가 아니라 START이고 NavigationView의 layout_gravity를 start로 바꾸면 오른쪽에서 생김
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onBackPressed() {  // 뒤로가기를 눌렀을 때
        if (binding.dlChat.isDrawerOpen(GravityCompat.END)) {  // 네이게이션 뷰가 열려있다면 닫아 주고 아니라면 뒤로가기
            binding.dlChat.closeDrawer(GravityCompat.END)
        } else if(binding.frameEmoticonChat.visibility == View.VISIBLE) {
            binding.frameEmoticonChat.visibility = View.GONE
        } else {
            super.onBackPressed()
        }
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