package com.example.jobis.presentation.study.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.jobis.R
import com.example.jobis.presentation.study.viewholder.MyStudyHeaderViewHolder
import com.example.jobis.presentation.study.viewholder.MyStudyViewHolder

class MyStudyAdapter(private val onClick: () -> Unit): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val MY_STUDY_HEADER = 0
    private val MY_STUDY_ITEM = 1
    private val MY_STUDY_FOOTER = 2

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            MY_STUDY_HEADER -> MyStudyHeaderViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_my_study_header, parent, false))
            else -> MyStudyViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_my_study, parent, false), onClick)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is MyStudyHeaderViewHolder -> {
                holder.bind("스터디")
            }
            is MyStudyViewHolder -> {
                holder.bind(position)
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (position) {
            0 -> MY_STUDY_HEADER
            else -> MY_STUDY_ITEM
        }
    }

    override fun getItemCount(): Int {
        return 5
    }

}