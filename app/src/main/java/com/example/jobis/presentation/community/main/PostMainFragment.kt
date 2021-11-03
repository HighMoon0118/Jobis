package com.example.jobis.presentation.community.main

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import com.example.jobis.R
import com.example.jobis.databinding.PostMainFragmentBinding
import com.example.jobis.presentation.community.PostList

class PostMainFragment : Fragment() {
    private lateinit var postMainViewModel: PostMainViewModel
    private var _binding: PostMainFragmentBinding? = null
    private val binding get() = _binding!!

    companion object {
        fun newInstance() = PostMainFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.post_main_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        postMainViewModel = ViewModelProvider(this, PostMainViewModelFactory())
            .get(PostMainViewModel::class.java)

        postMainViewModel.postList.observe(viewLifecycleOwner,
            Observer { postList ->
                postList ?: return@Observer
                updatePost(postList)
            })

        postMainViewModel.loadAllPosts()
    }

    private fun updatePost(postList: PostList) {
        // 리사이클러뷰로 화면에 뿌리기
    }
}