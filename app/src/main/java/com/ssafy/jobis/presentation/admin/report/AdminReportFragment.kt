package com.ssafy.jobis.presentation.admin.report

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.ssafy.jobis.R
import com.ssafy.jobis.data.repository.AdminRepository
import com.ssafy.jobis.data.response.ReportResponse
import com.ssafy.jobis.data.response.UserResponse
import com.ssafy.jobis.databinding.FragmentAdminReportBinding
import com.ssafy.jobis.presentation.admin.AdminViewModel
import java.lang.IllegalArgumentException

class AdminReportFragment : Fragment() {

    private val activityViewModel: AdminViewModel by activityViewModels {
        object: ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                if (modelClass.isAssignableFrom(AdminViewModel::class.java)) {
                    return AdminViewModel(
                        adminRepository = AdminRepository()
                    ) as T
                }
                throw IllegalArgumentException("UnKnown ViewModel Class")
            }
        }
    }

    private var _binding: FragmentAdminReportBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAdminReportBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        activityViewModel.reportList.observe(viewLifecycleOwner,
            Observer { reportList ->
                reportList ?: return@Observer
                updateReportList(reportList)
            })
    }
    private fun updateReportList(reportList: MutableList<ReportResponse>) {
        Log.d("test", "${reportList}")
    }
}