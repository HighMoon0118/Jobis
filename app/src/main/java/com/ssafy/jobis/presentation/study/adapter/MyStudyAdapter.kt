package com.ssafy.jobis.presentation.study.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ssafy.jobis.R
import com.ssafy.jobis.data.model.study.Study
import com.ssafy.jobis.presentation.study.viewholder.MyStudyHeaderViewHolder
import com.ssafy.jobis.presentation.study.viewholder.MyStudyViewHolder

class MyStudyAdapter(val context: Context): ListAdapter<Study, MyStudyViewHolder>(StudyDiffCallback) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyStudyViewHolder {
        return MyStudyViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_my_study, parent, false), context)
    }

    override fun onBindViewHolder(holder: MyStudyViewHolder, position: Int) {
        val study = getItem(position)
        holder.bind(study)
    }

}

object StudyDiffCallback : DiffUtil.ItemCallback<Study>() {
    override fun areItemsTheSame(oldItem: Study, newItem: Study): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: Study, newItem: Study): Boolean {
        return oldItem.id == newItem.id
    }
}