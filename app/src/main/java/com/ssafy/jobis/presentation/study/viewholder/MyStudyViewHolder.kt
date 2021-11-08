package com.ssafy.jobis.presentation.study.viewholder

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ssafy.jobis.R

class MyStudyViewHolder(view: View, private val onClick: () -> Unit): RecyclerView.ViewHolder(view) {


    private val myStudyTitle: TextView = view.findViewById(R.id.tv_my_study_title)
    private val myStudyMsg: TextView = view.findViewById(R.id.tv_my_study_msg)
    private val myStudyDate: TextView = view.findViewById(R.id.tv_my_study_date)
    private val myStudyMsgCnt: TextView = view.findViewById(R.id.tv_my_study_msg_cnt)
    private val myStudyDDay: TextView = view.findViewById(R.id.tv_my_study_dday)

    init {
        view.setOnClickListener{onClick()}
    }

    fun bind(id: Int) {
        myStudyTitle.text = "${id}번째 스터디"
    }
}