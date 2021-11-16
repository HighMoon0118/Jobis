package com.ssafy.jobis.presentation.study.adapter

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.ssafy.jobis.R
import com.ssafy.jobis.data.model.study.Study
import com.ssafy.jobis.presentation.chat.ChatLogActivity

class TmpAdapter(val studyList: List<Study>): RecyclerView.Adapter<TmpAdapter.CustomViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TmpAdapter.CustomViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.tmp_list_item, parent, false)

        return CustomViewHolder(view).apply {
            itemView.setOnClickListener{
                val curPos:Int = adapterPosition
                val study: Study = studyList.get(curPos)
                Toast.makeText(parent.context, "이름 : ${study.id}", Toast.LENGTH_SHORT).show()


                val intent = Intent(view.context, ChatLogActivity::class.java)
//                intent.putExtra("study_title", study.title)
                intent.putExtra("study_id", study.id.toString())
                view.context.startActivity(intent)
            }

        }
    }

    override fun onBindViewHolder(holder: TmpAdapter.CustomViewHolder, position: Int) {
        holder.name.text = studyList.get(position)!!.title
        holder.uid.text = studyList.get(position)!!.id.toString()
        holder.describe.text = studyList.get(position)!!.content

        Log.d("holder", holder.toString())
    }

    override fun getItemCount(): Int {
        return studyList.size
//        return 0
    }

    class CustomViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val name = itemView.findViewById<TextView>(R.id.tv_studyName)        // 이름
        val uid = itemView.findViewById<TextView>(R.id.tv_uid)          // 나이
        val describe = itemView.findViewById<TextView>(R.id.tv_studyDescribe)          // 직업

    }

}