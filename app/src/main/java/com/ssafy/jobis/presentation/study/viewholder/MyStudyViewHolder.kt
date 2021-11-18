package com.ssafy.jobis.presentation.study.viewholder

import android.content.Context
import android.content.Intent
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ssafy.jobis.R
import com.ssafy.jobis.data.model.study.Study
import com.ssafy.jobis.presentation.chat.ChatActivity

class MyStudyViewHolder(val view: View, val context: Context): RecyclerView.ViewHolder(view) {


    private val myStudyTitle: TextView = view.findViewById(R.id.tv_my_study_title)
    private val myStudyMsg: TextView = view.findViewById(R.id.tv_my_study_msg)
    private val myStudyDate: TextView = view.findViewById(R.id.tv_my_study_date)
    private val myStudyMsgCnt: TextView = view.findViewById(R.id.tv_my_study_msg_cnt)
    private val myStudyDDay: TextView = view.findViewById(R.id.tv_my_study_dday)

    fun bind(study: Study, dDay: Int?) {

        myStudyTitle.text = study.title
        if (dDay == null) {
            myStudyDDay.text = ""
        }
        else {
            myStudyDDay.text = "D-${dDay.toString()}"
        }
        view.setOnClickListener{
            val intent = Intent(context, ChatActivity::class.java)
            intent.putExtra("study_id", study.id)
            context.startActivity(intent)
        }
    }
}