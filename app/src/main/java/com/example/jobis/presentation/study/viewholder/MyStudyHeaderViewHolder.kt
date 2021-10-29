package com.example.jobis.presentation.study.viewholder

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.jobis.R

class MyStudyHeaderViewHolder(view: View): RecyclerView.ViewHolder(view) {

    private val headerTextView: TextView = view.findViewById(R.id.tv_my_study_header)


    fun bind(text: String) {
        headerTextView.text = text
    }

}