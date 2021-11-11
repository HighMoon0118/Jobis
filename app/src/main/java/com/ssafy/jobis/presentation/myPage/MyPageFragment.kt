package com.ssafy.jobis.presentation.myPage

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.ssafy.jobis.databinding.FragmentMyBinding
import com.ssafy.jobis.presentation.MainActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
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

        val auth = Firebase.auth



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

            } else {

            }
        }

        binding.commentToggleButton.setOnClickListener {
            val isChecked = binding.commentToggleButton.isChecked
            if (isChecked) {

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
}
