package com.example.jobis.presentation.community.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.jobis.data.repository.PostMainRepository
import java.lang.IllegalArgumentException

class PostMainViewModelFactory: ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PostMainViewModel::class.java)) {
            return PostMainViewModel(
                postMainRepository = PostMainRepository()
            ) as T
        }
        throw IllegalArgumentException("UnKnown ViewModel class")
    }
}