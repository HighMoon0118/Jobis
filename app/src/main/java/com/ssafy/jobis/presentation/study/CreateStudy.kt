package com.ssafy.jobis.presentation.study

import android.app.AlertDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.IgnoreExtraProperties
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.ssafy.jobis.R
import com.ssafy.jobis.databinding.ActivityCreateStudyBinding
import com.ssafy.jobis.databinding.AlertdialogEdittextBinding
import com.ssafy.jobis.databinding.CreateStudyBinding
import com.ssafy.jobis.presentation.MainActivity


class CreateStudy : AppCompatActivity() {

    private var mBinding: ActivityCreateStudyBinding? = null
    private val binding get() = mBinding!!

    var database = Firebase.database.reference
    val uid = FirebaseAuth.getInstance().uid


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mBinding = ActivityCreateStudyBinding.inflate(layoutInflater)

        setContentView(binding.root)


        binding.btnSubmit.setOnClickListener {
            val StudyName = binding.etTitle.text
            val StudyDescribe = binding.etDescribe.text
            CreateNewStudy(StudyName.toString(), StudyDescribe.toString(), uid.toString())
        }
    }

//    private fun RequestNewGroupChat() {
////        var Builder = AlertDialog.Builder(activity, R.style.ThemeOverlay_AppCompat_Dialog_Alert)
////        Builder.setTitle("스터디 생성")
////        Builder.setMessage("스터디 이름")
////
////
////
////        val alertDialog = Builder.create()
////        alertDialog.show()
//
//        val builder = AlertDialog.Builder(this)
//        val builderItem = AlertdialogEdittextBinding.inflate(layoutInflater)
//        val studyTitle = builderItem.etTitle
//        val studyDescribe = builderItem.etDescribe
//        val studyCreateButton = builderItem.btnSubmit
//
//        studyCreateButton.setOnClickListener {
//            var StudyName = studyTitle.text
//            var StudyDescribe = studyDescribe.text
//
//            if (StudyName.isBlank() || StudyDescribe.isBlank()) {
//                Toast.makeText(this, "항목을 모두 입력해주세요", Toast.LENGTH_SHORT).show()
//            } else {
//                CreateNewStudy(StudyName.toString(), StudyDescribe.toString(), Uid.toString())
////                Toast.makeText(activity, "submit", Toast.LENGTH_SHORT).show()
//            }
//        }
//
//        with(builder) {
//            setView(builderItem.root)
//            show()
//        }
//
//    }



    private fun CreateNewStudy(StudyName: String, StudyDescribe: String, Uid:String) {

//        var rootStorage = Firebase.storage.reference
        val Study = Study(StudyName, StudyDescribe, Uid)


        database.child("Study").child("${StudyName}_${Uid}").setValue(Study)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Toast.makeText(this, "${StudyName} is Created Successfully", Toast.LENGTH_SHORT).show()
                    var intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                    
                    
                    // 이걸로 MainActivity로 돌아가지 않고 MyStudyFragment로 돌아가게 해줌
                    finish()

                } else {
                    Toast.makeText(this, "Failed...", Toast.LENGTH_SHORT).show()
                }
            }
    }


    @IgnoreExtraProperties
    data class Study(val studyName:String, val studyDescribe:String, val uid:String) {
        constructor() : this("", "", "")
    }


}