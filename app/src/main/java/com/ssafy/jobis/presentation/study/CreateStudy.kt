package com.ssafy.jobis.presentation.study

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.room.Room
import androidx.room.Room.databaseBuilder
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.database.FirebaseDatabase
import com.ssafy.jobis.R
import com.ssafy.jobis.data.model.study.Crew
import com.ssafy.jobis.data.model.study.Study
import com.ssafy.jobis.data.model.study.StudyDatabase
import com.ssafy.jobis.databinding.ActivityCreateStudyBinding
import com.ssafy.jobis.presentation.MainActivity
import com.ssafy.jobis.presentation.chat.ChatActivity
import com.ssafy.jobis.presentation.chat.ChatLogActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.String.format
import java.text.SimpleDateFormat
import java.util.*


class CreateStudy : AppCompatActivity() {

    private var mBinding: ActivityCreateStudyBinding? = null
    private val binding get() = mBinding!!

    private val mAuth = FirebaseAuth.getInstance().currentUser

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityCreateStudyBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val region_list = resources.getStringArray(R.array.study_region)
        val region_adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item,region_list)

        val topic_list = resources.getStringArray(R.array.study_topic)
        val topic_adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item,topic_list)

        val population_list = resources.getStringArray(R.array.study_population)
        val population_adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item,population_list)

        binding.spRegion.adapter = region_adapter
        binding.spTopic.adapter = topic_adapter
        binding.spPopulation.adapter = population_adapter


        binding.btnSubmit.setOnClickListener {
            val title = binding.etTitle.text.toString()
            val content = binding.etDescribe.text.toString()
//            val location = binding.spRegion.text.toString()
//            val topic = binding.spTopic.text.toString()
//            val max_user = binding.spPopulation.text.
            val location = "지역1"
            val topic = "주제1"
            val max_user = 5
            val current_user = 1

            val username = Crew(mAuth!!.uid.toString())
            val user_list = listOf(username)
            val curTime = Calendar.getInstance().time
            val sdf = SimpleDateFormat("hh:mm a")
            val time = sdf.format(curTime)




            RequestNewStudy(title, content, location, topic, max_user, current_user, user_list, time, 0)
            finish()
        }
    }

    private fun RequestNewStudy(
        title:String,
        content:String? = null,
        location:String? = null,
        topic:String,
        max_user:Int? = null,
        current_user:Int = 1,
        user_list:List<Crew>? = null,
        created_at:String,
    unread_chat_cnt:Int? = 0) {


        val a = Study(
            title = title,
            content = content,
            location = location,
            topic = topic,
            max_user = max_user,
            current_user = current_user,
            user_list = user_list,
            created_at = created_at,
            unread_chat_cnt = unread_chat_cnt
        )


            val ref = FirebaseDatabase.getInstance().getReference("/Study")

            var pushedStudy = ref.push()
            Log.d("스터디 업로드", "스터디 업로드")
            pushedStudy.setValue(a)
                .addOnCompleteListener { task ->
                    Log.d("스터디 업로드 완료", "스터디 업로드 완료")
                    if (task.isSuccessful) {
                        Toast.makeText(applicationContext, pushedStudy.key , Toast.LENGTH_SHORT).show()


                        // 로컬에 만들어주고
                        CoroutineScope(Dispatchers.IO).launch {
                            a.id = pushedStudy.key.toString()
                            val db = StudyDatabase.getInstance(this@CreateStudy)
                            db!!.getStudyDao().insertStudy(a)

                            // firebase db에 key 업데이트 해줘야함
                            pushedStudy.setValue(a)

                            // 나중에 방 만들면 바로 넘어가는거 구현
                            var intent = Intent(applicationContext, ChatActivity::class.java)
                            intent.putExtra("study_id", a.id)
                            startActivity(intent)
                        }



                    } else {
                        Toast.makeText(applicationContext, "Failed...", Toast.LENGTH_SHORT).show()
                    }
                }.addOnFailureListener {
                    Log.d("스터디 만들기 실패", "실패 실패 실패")
                }
        }
    }

