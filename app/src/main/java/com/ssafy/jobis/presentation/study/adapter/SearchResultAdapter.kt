package com.ssafy.jobis.presentation.study.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.ssafy.jobis.R
import com.ssafy.jobis.presentation.study.SearchResult

class SearchResultAdapter(val searchResultList: ArrayList<SearchResult>) : RecyclerView.Adapter<SearchResultAdapter.CustomViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchResultAdapter.CustomViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.result_item, parent, false)
        return CustomViewHolder(view).apply{
            itemView.setOnClickListener{
                val curPos : Int = adapterPosition
                Toast.makeText(parent.context, "몇번째 : ${curPos}", Toast.LENGTH_SHORT ).show()
            }
        }
    }

    override fun onBindViewHolder(holder: SearchResultAdapter.CustomViewHolder, position: Int) {
        holder.title.text = searchResultList.get(position).title
        holder.describe.text = searchResultList.get(position).describe
        holder.startday.text = searchResultList.get(position).startday
        holder.population.text = searchResultList.get(position).population

    }

    override fun getItemCount(): Int {
        return searchResultList.size
    }

    class CustomViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val title = itemView.findViewById<TextView>(R.id.tv_title)   // 스터디 이름
        val describe = itemView.findViewById<TextView>(R.id.tv_describe)  // 스터디 설명
        val startday = itemView.findViewById<TextView>(R.id.tv_startday_value)  // 스터디 시작일
        val population = itemView.findViewById<TextView>(R.id.tv_population_value)  // 스터디 인원
    }

}