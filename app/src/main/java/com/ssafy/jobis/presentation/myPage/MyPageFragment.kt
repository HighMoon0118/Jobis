package com.ssafy.jobis.presentation.myPage

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.ssafy.jobis.databinding.FragmentMyBinding
import com.ssafy.jobis.presentation.MainActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import com.ssafy.jobis.data.model.community.Comment
import com.ssafy.jobis.data.response.PostResponse
import com.ssafy.jobis.data.response.PostResponseList
import com.ssafy.jobis.presentation.community.CustomPostAdapter
import com.ssafy.jobis.presentation.community.detail.CommunityDetailActivity
import com.ssafy.jobis.presentation.community.detail.ui.detail.CustomCommentAdapter
import com.ssafy.jobis.presentation.login.Jobis

class MyPageFragment: Fragment() {

    private lateinit var myPageViewModel: MyPageViewModel
    private var mainActivity: MainActivity? = null
    private var _binding: FragmentMyBinding? = null
    private val binding get() = _binding!!
    lateinit var auth: FirebaseAuth
    var db = FirebaseFirestore.getInstance()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentMyBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        myPageViewModel = ViewModelProvider(this, MyPageViewModelFactory())
            .get(MyPageViewModel::class.java)
        auth = Firebase.auth

        myPageViewModel.myLikeList.observe(viewLifecycleOwner,
            Observer { myLikeList ->
                myLikeList ?: return@Observer
                updateMyLike(myLikeList)
            })
        myPageViewModel.myPostList.observe(viewLifecycleOwner,
            Observer { myPostList ->
                myPostList ?: return@Observer
                updateMyPost(myPostList)
            })
        myPageViewModel.myCommentList.observe(viewLifecycleOwner,
            Observer { myCommentList ->
                myCommentList ?: return@Observer
                updateMyComment(myCommentList)
            })


        binding.myPageNickNameTextView.text = Jobis.prefs.getString("nickname", "??")
        binding.logoutButton.setOnClickListener {
            auth.signOut()
            Jobis.prefs.setString("nickname", "??")
            mainActivity?.goUserActivity()
        }

        binding.jobToggleButton.setOnClickListener {
            val isChecked = binding.jobToggleButton.isChecked
            if (isChecked) {
            } else {

            }
        }

        binding.likeToggleButton.setOnClickListener {
            val isChecked = binding.likeToggleButton.isChecked
            // 체크되어있으면 리사이클러뷰를 활성화시킨다.
            if (isChecked) {
                myPageViewModel.loadMyLikeList(auth.currentUser!!.uid)
            } else {

            }
        }

        binding.postToggleButton.setOnClickListener {
            val isChecked = binding.postToggleButton.isChecked
            if (isChecked) {
                myPageViewModel.loadMyPostList(auth.currentUser!!.uid)
            } else {

            }
        }

        binding.commentToggleButton.setOnClickListener {
            val isChecked = binding.commentToggleButton.isChecked
            if (isChecked) {
                myPageViewModel.loadMyCommentList(auth.currentUser!!.uid)
            } else {

            }
        }

    }



    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is MainActivity) mainActivity = context
    }

    fun updateMyLike(postList: PostResponseList) {
        val adapter = CustomPostAdapter()
        adapter.listData = postList
        adapter.setOnItemClickListener(object: CustomPostAdapter.OnItemClickListener{
            override fun onItemClick(v: View, post: PostResponse, pos: Int) {
                if (post.id != null) {
                    (activity as MainActivity).goMyPageDetail(post.id)
                }
            }
        })

        binding.myLikeRecyclerView.adapter = adapter
        binding.myLikeRecyclerView.layoutManager = StaggeredGridLayoutManager(
            1,
            StaggeredGridLayoutManager.VERTICAL)
    }

    fun updateMyPost(postList: PostResponseList) {
        val adapter = CustomPostAdapter()
        adapter.listData = postList
        adapter.setOnItemClickListener(object: CustomPostAdapter.OnItemClickListener{
            override fun onItemClick(v: View, post: PostResponse, pos: Int) {
                if (post.id != null) {
                    (activity as MainActivity).goMyPageDetail(post.id)
                }
            }
        })

        binding.myPostRecyclerView.adapter = adapter
        binding.myPostRecyclerView.layoutManager = StaggeredGridLayoutManager(
            1,
            StaggeredGridLayoutManager.VERTICAL)
    }

    fun updateMyComment(commentList: MutableList<Comment>) {
        val adapter = CustomCommentAdapter()
        adapter.listData = commentList
        adapter.setOnItemClickListener(object: CustomCommentAdapter.OnItemClickListener{
            override fun onItemClick(v: View, comment: Comment, pos: Int) {
                (activity as MainActivity).goCommunityDetailActivity(comment.post_id)
            }
        })
        binding.myCommentRecyclerView.adapter = adapter
        binding.myCommentRecyclerView.layoutManager = StaggeredGridLayoutManager(
            1,
            StaggeredGridLayoutManager.VERTICAL)
    }
}
