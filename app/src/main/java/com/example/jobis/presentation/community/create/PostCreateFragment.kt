package com.example.jobis.presentation.community.create

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.jobis.data.model.Post
import com.example.jobis.databinding.PostCreateFragmentBinding
import com.example.jobis.presentation.MainActivity
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase

class PostCreateFragment : Fragment() {

    companion object {
        fun newInstance() = PostCreateFragment()
    }
    private lateinit var mainAcitivty: MainActivity
    private var _binding: PostCreateFragmentBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = PostCreateFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.createPostButton.setOnClickListener {
            val currentUser = Firebase.auth.currentUser
            if (currentUser != null) {
                // firebase 요청 보내기
                val content = binding.editTextPostContent.text.toString()
                val title = binding.editTextPostTitle.text.toString()
                val post = Post(
                    title=title,
                    content=content,
                    user_id=currentUser.uid
                )
                var db = FirebaseFirestore.getInstance()
                db.collection("posts")
                    .add(post)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            // 성공 시 현재 프래그먼트 닫기
                            val appContext = context?.applicationContext
                            Toast.makeText(appContext, "게시글이 등록되었습니다.", Toast.LENGTH_LONG).show()
                            mainAcitivty?.goCommunityFragment()

                        } else {
                            // 실패 시 게시글 등록 실패 띄우기
                            val appContext = context?.applicationContext
                            Toast.makeText(appContext, "게시글 등록에 실패했습니다.", Toast.LENGTH_LONG).show()
                        }
                    }
            } else {
                val appContext = context?.applicationContext
                Toast.makeText(appContext, "존재하지 않는 사용자입니다.", Toast.LENGTH_LONG).show()
            }
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is MainActivity) mainAcitivty = context
    }
}