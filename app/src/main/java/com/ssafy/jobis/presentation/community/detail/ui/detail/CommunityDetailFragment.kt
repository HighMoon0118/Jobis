package com.ssafy.jobis.presentation.community.detail.ui.detail

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.ssafy.jobis.R
import com.ssafy.jobis.data.model.community.Comment
import com.ssafy.jobis.data.response.PostResponse
import com.ssafy.jobis.databinding.CommunityDetailFragmentBinding
import java.text.SimpleDateFormat

class CommunityDetailFragment : Fragment() {
    private lateinit var communityDetailViewModel: CommunityDetailViewModel
    private var _binding: CommunityDetailFragmentBinding? = null
    private val binding get() = _binding!!
    private var uid: String? = null
    var id: String? = null
    companion object {
        fun newInstance() = CommunityDetailFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = CommunityDetailFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        communityDetailViewModel = ViewModelProvider(this, CommunityDetailViewModelFactory())
            .get(CommunityDetailViewModel::class.java)
        communityDetailViewModel.post.observe(viewLifecycleOwner,
            Observer { post ->
                post ?: return@Observer
                updateUi(post)
            })
        communityDetailViewModel.isLiked.observe(viewLifecycleOwner,
            Observer { isLiked ->
                isLiked ?: return@Observer
                updateLikeUi(isLiked)
            })
        communityDetailViewModel.likeCount.observe(viewLifecycleOwner,
            Observer { likeCount ->
                likeCount ?: return@Observer
                binding.detailLikeCountTextView.text = likeCount.toString()
            })
        communityDetailViewModel.comments.observe(viewLifecycleOwner,
            Observer { comments ->
                comments ?: return@Observer
                updateCommentsUi(comments)
            })
        id = arguments?.getString("id")
        uid = Firebase.auth.currentUser?.uid
        Log.d("test", "testet ${id} ${uid}")
        binding.detailLikeImageView.setOnClickListener {
            communityDetailViewModel.updateLike(id!!, uid!!)
        }

        binding.commentButton.setOnClickListener {
            val text = binding.commentEditText.text.toString()
            if (text.length > 0) {
                communityDetailViewModel.createComment(text, id!!, uid!!)
                binding.commentEditText.text.clear()
            }
        }
        if (id != null && uid != null) {
            communityDetailViewModel.loadPost(id!!, uid!!)
        }
    }

    private fun updateUi(post: PostResponse) {
        binding.detailTitleTextView.text = post.title
        binding.detailCategoryTextView.text = post.category
        binding.detailContentTextView.text = post.content
        val sdf = SimpleDateFormat("yyyy-MM-dd hh:mm")
        binding.detailDateTextView.text = sdf.format(post.created_at.toDate()).toString()
        binding.detailLikeCountTextView.text = post.like.size.toString()
        binding.detailNickNameTextView.text = post.user_nickname
        if (post.user_id == uid) {
            binding.postEditButton.visibility = View.VISIBLE
            binding.postDeleteButton.visibility = View.VISIBLE
        }
    }

    private fun updateLikeUi(isLiked: Boolean) {
        if (isLiked) {
            binding.detailLikeImageView.circleBackgroundColor = resources.getColor(R.color.primary)
        } else {
            binding.detailLikeImageView.circleBackgroundColor = resources.getColor(R.color.light_gray)
        }
    }

    private fun updateCommentsUi(comments: MutableList<Comment>) {
        val adapter = CustomCommentAdapter()
        Log.d("test", "updatecommentUI ${comments[0].javaClass}")
        adapter.listData = comments
        binding.detailCommentRecyclerView.adapter = adapter
        binding.detailCommentRecyclerView.layoutManager = StaggeredGridLayoutManager(
            1,
            StaggeredGridLayoutManager.VERTICAL)
    }

}