package com.ssafy.jobis.presentation.study.adapter

import android.content.Context
import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_NEW_TASK
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.ssafy.jobis.R
import com.ssafy.jobis.data.model.study.Study
import com.ssafy.jobis.data.model.study.StudyDatabase
import com.ssafy.jobis.presentation.chat.ChatActivity
import com.ssafy.jobis.presentation.chat.ChatLogActivity
import com.ssafy.jobis.presentation.study.CreateStudy
import com.ssafy.jobis.presentation.study.SearchResult
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import android.os.Looper




class SearchResultAdapter(val context: Context, val searchResultList: ArrayList<Study>?) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.result_item, parent, false)
        return CustomViewHolder(view)
            .apply{
            itemView.setOnClickListener{
                CoroutineScope(Dispatchers.IO).launch {
                    val curPos:Int = adapterPosition
                    val db = StudyDatabase.getInstance(parent.context)
                    db!!.getStudyDao().insertStudy(searchResultList!![curPos])


                    // 나중에 방 만들면 바로 넘어가는거 구현
                    var intent = Intent(context, ChatActivity::class.java)
                    intent.putExtra("study_id", searchResultList[curPos].id)
                    context.startActivity(intent.addFlags(FLAG_ACTIVITY_NEW_TASK))
                }
            }
        }
        }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(holder) {
            is CustomViewHolder -> {
                if (searchResultList != null) {
                holder.bind(searchResultList[position])
            }}
        }

    }

    override fun getItemCount(): Int {
        return searchResultList?.size?:0
    }

    class CustomViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        init {
            itemView.setOnClickListener {

            }
        }

        val title = itemView.findViewById<TextView>(R.id.tv_title)   // 스터디 이름
        val describe = itemView.findViewById<TextView>(R.id.tv_describe)  // 스터디 설명
        val startday = itemView.findViewById<TextView>(R.id.tv_startday_value)  // 스터디 시작일
        val population = itemView.findViewById<TextView>(R.id.tv_population_value)  // 스터디 인원

        fun bind(study: Study) {
            title.text = study.title
            describe.text = study.content
            startday.text = study.created_at
            population.text = study.current_user.toString()
        }

    }

}