package com.ssafy.jobis.presentation.study

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.SystemClock
import android.util.Log
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.gson.Gson
import com.ssafy.jobis.data.model.study.Crew
import com.ssafy.jobis.data.model.study.Study
import com.ssafy.jobis.databinding.ActivitySearchStudyBinding
import com.ssafy.jobis.presentation.study.adapter.MyStudyAdapter
import com.ssafy.jobis.presentation.study.adapter.SearchResultAdapter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch


class SearchStudy : AppCompatActivity() {

    private var mBinding: ActivitySearchStudyBinding? = null
    private val binding get() = mBinding!!


    private var studyList = arrayListOf<Study>()

    var handler: Handler? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mBinding = ActivitySearchStudyBinding.inflate(layoutInflater)
        setContentView(binding.root)




        binding.svSearch.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {

                // 검색 버튼 누를 때 호출
//                fetchStudy()
                initRecycler()
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {

                // 검색창에서 글자가 변경이 일어날 때마다 호출

                return true
            }
        })

//        handler = Handler()
//        var thread = ThreadClass()
//        handler?.post(thread)

    }

    private fun fetchStudy() {
        val ref = FirebaseDatabase.getInstance().getReference("/Study")

        Toast.makeText(applicationContext, "searched", Toast.LENGTH_SHORT).show()

        ref.addListenerForSingleValueEvent(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (info in snapshot.children) {

                    // 와 이게되네...
                    val study = info.getValue(Study::class.java)
                    studyList?.add(study!!)
                }
                val searchAdapter = SearchResultAdapter(applicationContext, studyList)
                binding.rvSearchResult.adapter = searchAdapter
                Log.i("checkData", studyList.toString())
            }
            override fun onCancelled(error: DatabaseError) {
            }
        })
    }

    fun initRecycler() {
        fetchStudy()
//        Log.i("chech null", studyList.toString())

//            val searchAdapter = SearchResultAdapter(studyList)
//            binding.rvSearchResult.adapter = searchAdapter

    }

//    inner class ThreadClass : Thread() {
//
//        override fun run() {
//            Toast.makeText(applicationContext, "halder callsed", Toast.LENGTH_SHORT).show()
//            searchAdapter = SearchResultAdapter(studyList)
//            binding.rvSearchResult.adapter = searchAdapter
//            handler?.post(this)
//        }
//    }
}

