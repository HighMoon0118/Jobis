package com.example.jobis.presentation.community

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.jobis.data.model.Post
import com.example.jobis.data.response.PostResponse
import com.example.jobis.databinding.PostRecyclerBinding
import java.text.SimpleDateFormat

class CustomPostAdapter: RecyclerView.Adapter<CustomPostAdapter.Holder>() {

    var listData = mutableListOf<PostResponse>()

    interface OnItemClickListener{
        fun onItemClick(v: View, post: PostResponse, pos: Int)
    }
    private var listener: OnItemClickListener? = null
    fun setOnItemClickListener(listener: OnItemClickListener) {
        this.listener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomPostAdapter.Holder {
        val binding = PostRecyclerBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return Holder(binding)
    }

    override fun onBindViewHolder(holder: CustomPostAdapter.Holder, position: Int) {
        val post = listData.get(position)
        holder.setPost(post)
    }

    override fun getItemCount(): Int {
        return listData.size
    }

    inner class Holder(val binding: PostRecyclerBinding): RecyclerView.ViewHolder(binding.root) {
        fun setPost(post: PostResponse) {
            val sdf = SimpleDateFormat("yyyy-MM-dd-hh-mm")
            binding.postCategoryText.text = "임시"
            binding.postCommentText.text = "댓글 " + post.comment_list.size.toString()
            binding.postContentText.text = post.content
            binding.postLikeText.text = "좋아요 " + post.like.size.toString()
            binding.postTimeText.text = sdf.format(post.created_at.toDate()).toString()
            binding.postTitleText.text = post.title

            val pos = adapterPosition
            if (pos!=RecyclerView.NO_POSITION) {
                itemView.setOnClickListener {
                    listener?.onItemClick(itemView, post, pos)
                }
            }
        }
    }

}