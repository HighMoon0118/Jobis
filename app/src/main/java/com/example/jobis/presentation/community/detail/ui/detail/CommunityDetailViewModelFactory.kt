package com.example.jobis.presentation.community.detail.ui.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.jobis.data.repository.CommunityRepository


class CommunityDetailViewModelFactory: ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CommunityDetailViewModel::class.java)) {
            return CommunityDetailViewModel(
                communityRepository = CommunityRepository()
            ) as T
        }
        throw IllegalArgumentException("UnKnown ViewModel class")
    }
}