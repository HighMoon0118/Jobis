package com.ssafy.jobis.presentation.community

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ssafy.jobis.data.response.PostResponseList
import com.ssafy.jobis.databinding.PostViewpagerBinding

class CommunityPagerAdapter: RecyclerView.Adapter<Holder>() {
    var postList = listOf<PostResponseList>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val binding = PostViewpagerBinding.inflate(LayoutInflater.from(parent.context),
        parent, false)
        return Holder(binding)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val post = postList[position]
        holder.setPostList(post)
    }

    override fun getItemCount(): Int {
        return postList.size
    }

}

class Holder(val binding: PostViewpagerBinding): RecyclerView.ViewHolder(binding.root) {
    fun setPostList(postList: PostResponseList) {

    }
}