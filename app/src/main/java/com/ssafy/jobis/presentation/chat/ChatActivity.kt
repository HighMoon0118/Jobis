package com.ssafy.jobis.presentation.chat

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.ImageDecoder
import android.graphics.Point
import android.graphics.Rect
import android.graphics.drawable.AnimatedImageDrawable
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.FirebaseAuth
import com.google.android.material.navigation.NavigationView
import com.google.firebase.ktx.Firebase
import com.google.firebase.messaging.ktx.messaging
import com.jaredrummler.android.colorpicker.ColorPickerDialog
import com.jaredrummler.android.colorpicker.ColorPickerDialogListener
import com.ssafy.jobis.R
import com.ssafy.jobis.data.model.study.Chat
import com.ssafy.jobis.databinding.ActivityChatBinding
import com.ssafy.jobis.presentation.chat.MyFCMService.Companion.CHANNEL_ID
import com.ssafy.jobis.presentation.chat.MyFCMService.Companion.CHANNEL_NAME
import com.ssafy.jobis.presentation.chat.MyFCMService.Companion.currentStudyId
import com.ssafy.jobis.presentation.chat.adapter.ChatAdapter
import com.ssafy.jobis.presentation.chat.adapter.GridAdapter
import com.ssafy.jobis.presentation.chat.adapter.ViewPagerAdapter
import com.ssafy.jobis.presentation.chat.viewholder.GIFViewHolder
import com.ssafy.jobis.presentation.chat.viewmodel.ChatViewModel
import com.ssafy.jobis.presentation.study.StudyViewModel
import com.ssafy.jobis.presentation.study.adapter.MyStudyAdapter
import com.ssafy.jobis.view.DrawingView
import kotlinx.coroutines.*
import java.util.*


class ChatActivity: AppCompatActivity(), View.OnClickListener, ColorPickerDialogListener,
    ViewPagerAdapter.CanvasListener, GIFViewHolder.OnClickGIFListener,
    ChatAdapter.onAddedChatListener, NavigationView.OnNavigationItemSelectedListener {

    private lateinit var binding: ActivityChatBinding
    private var mySource: ImageDecoder.Source? = null
    private lateinit var chatAdapter: ChatAdapter
    private val viewPagerAdapter: ViewPagerAdapter by lazy {
        ViewPagerAdapter(this@ChatActivity)
    }
    private val girdAdapter: GridAdapter by lazy {
        GridAdapter(this@ChatActivity)
    }

    private lateinit var model: ChatViewModel
    private lateinit var userId: String


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityChatBinding.inflate(layoutInflater)
        setContentView(binding.root)

        currentStudyId = intent.getStringExtra("study_id").toString()
        Log.d("액티비티에서 얻은 스터디 아이디", currentStudyId)
        userId = FirebaseAuth.getInstance().currentUser!!.uid

        model = ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory(application)).get(ChatViewModel::class.java)

        model.studyWithChats.observe(this,  {
            chatAdapter = ChatAdapter(it.chats)
            binding.rvChat.adapter = chatAdapter
        })

        val display = windowManager.defaultDisplay
        val size = Point()
        display.getRealSize(size)
        val density = resources.displayMetrics.density
        val width = (size.x - 300*density)/4

        binding.gridEmoticonChat.apply {
            adapter = girdAdapter
            layoutManager = GridLayoutManager(context, 3)
            addItemDecoration(object : RecyclerView.ItemDecoration() {
                val spanCount = 3
                val spacing = width.toInt()
                override fun getItemOffsets( outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
                    val position: Int = parent.getChildAdapterPosition(view)
                    val column: Int = position % spanCount

                    outRect.apply {
                        left = spacing - column * spacing / spanCount
                        right = (column + 1) * spacing / spanCount

                        if (position < spanCount) top = spacing
                        bottom = spacing
                    }
                }
            })
        }

        binding.viewpagerChat.apply {
            isUserInputEnabled = false
            adapter = viewPagerAdapter
            registerOnPageChangeCallback(object: ViewPager2.OnPageChangeCallback() {
                override fun onPageScrollStateChanged(state: Int) {
                    super.onPageScrollStateChanged(state)
                    if (viewPagerAdapter.isAdded(currentItem)) {
                        binding.imgCheck.setImageResource(R.drawable.ic_check_circle_24)
                    } else {
                        binding.imgCheck.setImageResource(R.drawable.ic_check_circle_outline_24)
                    }
                }
            })
        }

        setSupportActionBar(binding.tbChat)  // 액션바 설정
        val actionbar = supportActionBar

        actionbar?.apply {
            setDisplayShowTitleEnabled(false)  // 앱 이름 안보이게
            setDisplayHomeAsUpEnabled(true)  // 뒤로가기 버튼
            setHomeAsUpIndicator(R.drawable.ic_arrow_back_24)  // 뒤로가기 아이콘 설정
        }

        Firebase.messaging.subscribeToTopic("StudyRoom")

        binding.apply {
            imgAddChat.setOnClickListener(this@ChatActivity)
            imgEmoticonChat.setOnClickListener(this@ChatActivity)
            imgSendChat.setOnClickListener(this@ChatActivity)
            editTextChat.requestFocus()
            editTextChat.setOnClickListener(this@ChatActivity)
            editTextChat.addTextChangedListener {
                if (it.toString().trim().isNotEmpty()) {
                    binding.imgSendChat.setColorFilter(Color.parseColor("#448aff"))
                } else {
                    binding.imgSendChat.setColorFilter(Color.parseColor("#7C7C7C"))
                }
            }
            imgSelectColor.setOnClickListener(this@ChatActivity)
            imgSave.setOnClickListener(this@ChatActivity)
            imgLeft.setOnClickListener(this@ChatActivity)
            imgRight.setOnClickListener(this@ChatActivity)
            imgCheck.setOnClickListener(this@ChatActivity)
            imgCloseGif.setOnClickListener(this@ChatActivity)
            chatNavigation.setNavigationItemSelectedListener(this@ChatActivity)
        }
    }

    private fun goToRecentChat() {
        CoroutineScope(Dispatchers.Main).launch {
            binding.rvChat.scrollToPosition(chatAdapter.itemCount-1)
        }
    }

    suspend fun showCanvas(view: View, imm: InputMethodManager) {
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_NOTHING)   // 키보드가 layout에 영향을 주지 않게 함
        binding.frameEmoticonChat.visibility = View.VISIBLE                             // 캔버스를 보임
        imm.hideSoftInputFromWindow(view.windowToken, 0)                           // 키보드를 숨김
        delay(100)                                                              // 키보드가 전부 안보일 떄까지 기다림
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)    // 키보드가 layout에 영향을 주도록 함
    }

    suspend fun showKeyboard(imm: InputMethodManager) {
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_NOTHING)   // 키보드가 layout에 영향을 주지 않게 함
        imm.showSoftInput(binding.editTextChat, 0)                                 // 키보드를 보임
        delay(100)                                                              // 키보드가 전부 보일 떄까지 기다림
        binding.frameEmoticonChat.visibility = View.GONE                                // 캔버스를 숨김
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)    // 키보드가 layout에 영향을 주도록 함
    }

    @SuppressLint("ResourceType")
    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.img_add_chat -> {

            }
            R.id.img_emoticon_chat -> {
                val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                val scope = CoroutineScope(Dispatchers.Main)
                scope.launch {
                    if (binding.frameEmoticonChat.visibility == View.GONE) {
                        showCanvas(view, imm)
                        binding.imgEmoticonChat.setImageResource(R.drawable.ic_create_24)
                    } else if(binding.gridEmoticonChat.visibility == View.GONE) {
                        binding.gridEmoticonChat.visibility = View.VISIBLE
                        binding.imgEmoticonChat.setImageResource(R.drawable.ic_create_24)
                    } else {
                        binding.gridEmoticonChat.visibility = View.GONE
                        binding.imgEmoticonChat.setImageResource(R.drawable.ic_faces_24)
                    }
                }
            }
            R.id.img_send_chat -> {
                if (mySource != null) {
//                    chatAdapter.addChat(true, mySource, null)
                    clearGIFLayout()
                }
                val text = binding.editTextChat.text?.trim().toString()
                if (text != "") {
//                    chatAdapter.addChat(false, null, text)
                    binding.editTextChat.text = null
                    model.sendMessage(currentStudyId, userId, text)
                }
            }
            R.id.edit_text_chat -> {
                val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                val scope = CoroutineScope(Job() + Dispatchers.Main)
                scope.launch {
                    if (binding.frameEmoticonChat.visibility == View.VISIBLE) {    // 1. Edit Text만 보일 때
                        showKeyboard(imm)                                             // 키보드를 보임
                    }
                }
            }
            R.id.img_select_color -> {
                ColorPickerDialog
                    .newBuilder()
                    .setDialogTitle(R.string.color_dialog_title)
                    .setSelectedButtonText(R.string.select)
                    .setCustomButtonText(R.string.custom)
                    .setPresetsButtonText(R.string.presets)
                    .show(this)
            }
            R.id.img_save -> {
                viewPagerAdapter.saveView()
            }
            R.id.img_left -> {
                binding.viewpagerChat.apply {
                    if (currentItem > 0) {
                        currentItem--
                    }
                    binding.textViewpagerNum.text = "${currentItem+1}/24"
                }
            }
            R.id.img_right -> {
                binding.viewpagerChat.apply {
                    if (currentItem < 23) {
                        currentItem++
                    }
                    binding.textViewpagerNum.text = "${currentItem+1}/24"
                }
            }
            R.id.img_check -> {
                val current = binding.viewpagerChat.currentItem
                viewPagerAdapter.apply {
                    if (isAdded(current)) {
                        removeView(current)
                        binding.imgCheck.setImageResource(R.drawable.ic_check_circle_outline_24)
                    } else {
                        addView(current)
                        binding.imgCheck.setImageResource(R.drawable.ic_check_circle_24)
                    }
                }

            }
            R.id.img_close_gif -> {
                clearGIFLayout()
            }
        }
    }

    fun clearGIFLayout() {
        binding.layoutGif.visibility = View.GONE
        binding.imgSendChat.setColorFilter(Color.parseColor("#7C7C7C"))
        mySource = null
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

    override fun onColorSelected(dialogId: Int, color: Int) {
        binding.imgSelectColor.background.setTint(color)
        DrawingView.color = color
    }

    override fun onDialogDismissed(dialogId: Int) {

    }

    override fun onSuccess() {
        girdAdapter.getGif()
    }

    override fun chooseGIF(source: ImageDecoder.Source?) {
        if (source != null) {
            mySource = source
            val drawable = ImageDecoder.decodeDrawable(mySource!!)
            binding.imgGif.setImageDrawable(drawable)

            if (drawable is AnimatedImageDrawable) {
                drawable.repeatCount = 4
                drawable.start()
            }
            binding.layoutGif.visibility = View.VISIBLE
            binding.imgSendChat.setColorFilter(Color.parseColor("#448aff"))
        }
    }

    override fun onAddedChat() {
        goToRecentChat()
    }

    override fun onPause() {
        super.onPause()
        currentStudyId = ""
    }
    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        if (R.id.item_1 == item.itemId) {
            startActivity(Intent(this, ChatScheduleActivity::class.java))
        }
        return true
    }
}