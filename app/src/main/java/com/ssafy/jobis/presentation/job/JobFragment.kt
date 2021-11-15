package com.ssafy.jobis.presentation.job

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.ssafy.jobis.databinding.FragmentJobBinding

class JobFragment: Fragment() {

    private lateinit var jobViewModel: JobViewModel
    private var _binding: FragmentJobBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentJobBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        jobViewModel = ViewModelProvider(this, JobViewModelFactory())
            .get(JobViewModel::class.java)


    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}