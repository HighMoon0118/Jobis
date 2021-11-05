package com.example.jobis.presentation.chat

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import com.example.jobis.R
import com.example.jobis.databinding.ActivityChatBinding
import com.example.jobis.presentation.chat.adapter.ChatAdapter

class ChatActivity: AppCompatActivity() {

    private lateinit var binding: ActivityChatBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityChatBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val recycler = binding.rvChat
        val chatAdapter = ChatAdapter()
        recycler.adapter = chatAdapter

        setSupportActionBar(binding.tbChat)  // 액션바 설정
        val actionbar = supportActionBar

        actionbar?.apply {
            setDisplayShowTitleEnabled(false)  // 앱 이름 안보이게
            setDisplayHomeAsUpEnabled(true)  // 뒤로가기 버튼
            setHomeAsUpIndicator(R.drawable.ic_arrow_back_24)  // 뒤로가기 아이콘 설정
        }

        recycler.post {
            recycler.scrollToPosition(19)
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
        } else {
            super.onBackPressed()
        }
    }
}