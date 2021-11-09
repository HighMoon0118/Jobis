package com.ssafy.jobis.presentation

import android.app.Activity
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import com.ssafy.jobis.R
import com.ssafy.jobis.databinding.FragmentRoutineScheduleBinding
import kotlinx.android.synthetic.main.activity_schedule.*
import kotlinx.android.synthetic.main.fragment_routine_schedule.*


class RoutineScheduleFragment(val activity: Activity) : Fragment() {
    private var _binding: FragmentRoutineScheduleBinding? = null
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!


    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentRoutineScheduleBinding.inflate(inflater, container, false)
        val view = binding.root

        // null 오류 > view binding 으로 해결했음 (왜?)

        ArrayAdapter.createFromResource(activity,
            R.array.dayOfWeek,
            android.R.layout.simple_spinner_item).also{adapter->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            binding.testSpinner.adapter = adapter
        }

//        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
//            override fun onNothingSelected(parent: AdapterView<*>?) {
//            }
//            override fun onItemSelected(
//                parent: AdapterView<*>?,
//                view: View?,
//                position: Int,
//                id: Long
//            ) {
//
//            }
//        }



        return view





    }
}
//  프래그먼트 유지  삭제 등 onAttach, onDetach 메서드를 오버라이딩