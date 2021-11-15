package com.ssafy.jobis.presentation.chat

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.widget.ScrollView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.*
import com.google.firebase.ktx.Firebase
import com.ssafy.jobis.R
import com.ssafy.jobis.databinding.ActivityChatLogBinding
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.HashMap

class ChatLogActivity : AppCompatActivity() {
    private var mBinding:ActivityChatLogBinding? = null
    private val binding get() = mBinding!!

    private val mAuth = FirebaseAuth.getInstance()

    private val user =  Firebase.auth.currentUser
    private var currentDate:String? = null
    private var currentTime:String? = null

    private lateinit var StudyNameRef : DatabaseReference
    private lateinit var StudyMessageKeyRef : DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mBinding = ActivityChatLogBinding.inflate(layoutInflater)

        setContentView(binding.root)

        val studyName = intent.getStringExtra("studyName").toString()

        binding.groupChatBarLayout.text = studyName
        StudyNameRef = FirebaseDatabase.getInstance().getReference().child("Study").child(studyName)
        Log.d("studyname", StudyNameRef.toString())
        GetUserInfo()

        binding.sendBtn.setOnClickListener {
            SaveMessageInfoToDataBase()
            
            // 입력칸 빈칸으로 만듬
            binding.inputGroupMessage.setText(null)
            
            // 스크롤 최하단
            binding.myScrollView.fullScroll(ScrollView.FOCUS_DOWN)
        }
    }

    override fun onStart() {
        super.onStart()
        StudyNameRef.addChildEventListener(object : ChildEventListener {
            override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                if (snapshot.exists()){
                    DisplayMessages(snapshot)
                }
            }

            override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {
                if (snapshot.exists()){
                    DisplayMessages(snapshot)
                }
            }

            override fun onChildRemoved(snapshot: DataSnapshot) {
            }

            override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {
            }

            override fun onCancelled(error: DatabaseError) {
            }

        })


    }




    private fun GetUserInfo() {
        user?.let {
            val username = user.displayName
            val email = user.email
            val uid = user.uid
        }
    }


    private fun SaveMessageInfoToDataBase() {
        val message = binding.inputGroupMessage.text
        val messageKey = StudyNameRef.push().key

        if (TextUtils.isEmpty(message)){
            Toast.makeText(this, "Please write a message first...", Toast.LENGTH_SHORT).show()
        }
        else {
            val calForDate = Calendar.getInstance()
            val currentDateFormat = SimpleDateFormat("MMM dd, yyyy")
            currentDate = currentDateFormat.format(calForDate.time)

            val calForTime = Calendar.getInstance()
            val currentTimeFormat = SimpleDateFormat("hh:mm a")
            currentTime = currentTimeFormat.format(calForTime.time)

            val groupMessageKey = HashMap<String, Any>()
            StudyNameRef.updateChildren(groupMessageKey)

            StudyMessageKeyRef = StudyNameRef.child(messageKey.toString())

            
            // put하는 시기에 바뀔 수 있다고 해서 고정된값으로 재할당 후 넣어줌
            val nCurrentDate = currentDate
            val nCurrentTime = currentTime

//            val MessageInfoMap = HashMap<String, Any>()
//            MessageInfoMap.put("name", user?.email!!)
//            MessageInfoMap.put("message", message)
//            MessageInfoMap.put("date", nCurrentDate!!)
//            MessageInfoMap.put("time", nCurrentTime!!)
            val MessageInfo = MessageInfoMap(user?.email, message.toString(), nCurrentDate, nCurrentTime)
            val MessageInfoValuse = MessageInfo.toMap()

            val childUpdates = hashMapOf<String, Any>(
                "message" to MessageInfoValuse
            )

            StudyMessageKeyRef.updateChildren(childUpdates)
//            Toast.makeText(this, "${currentTime} ${currentDate}", Toast.LENGTH_SHORT).show()
        }
    }

    class MessageInfoMap (
        var name:String? = null,
        var message:String? = null,
        var date:String? = null,
        var time:String? = null
            ) {
        @Exclude
        fun toMap() : Map<String, Any?> {
            return mapOf(
                "name" to name,
                "message" to message,
                "date" to date,
                "time" to time
            )
        }
    }

    private fun DisplayMessages(snapshot: DataSnapshot) {
        val iterator:Iterator<DataSnapshot> = snapshot.children.iterator()

        for (child in iterator) {
            val chatDate = child.child("date").getValue()
            val chatTime = child.child("time").getValue()
            val name = child.child("name").getValue()
            val message = child.child("message").getValue()

            binding.groupChatDisplay.append("${name}: \n${message} \n${chatDate} ${chatTime}\n\n")

            // 스크롤 최하단으로 자동 내리기
            binding.myScrollView.fullScroll(ScrollView.FOCUS_DOWN)
//            Log.d("recieve", "${chatDate} ${chatTime} ${name} ${message}")
        }
    }

}