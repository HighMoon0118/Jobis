package com.example.jobis.presentation.myPage

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.jobis.databinding.FragmentMyBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase

class MyPageFragment: Fragment() {


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
        val auth = Firebase.auth
        val currentUser = auth.currentUser
        Log.d("test","${currentUser} ${currentUser?.uid}")
        val docRef = db.collection("users")
            .document(currentUser!!.uid)
        docRef.get()
            .addOnSuccessListener { document ->
                if (document != null) {
                    Log.d("test", "성공 ${document.data}")
                } else {
                    Log.d("test", "no such document")
                }
            }
            .addOnFailureListener { exception ->
                Log.d("test", "${exception}")
            }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}
