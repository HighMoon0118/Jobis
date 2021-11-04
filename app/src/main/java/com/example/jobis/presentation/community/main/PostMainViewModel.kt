package com.example.jobis.presentation.community.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.jobis.data.repository.PostMainRepository
import com.example.jobis.presentation.community.PostList
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PostMainViewModel(private val postMainRepository: PostMainRepository) : ViewModel() {
    // TODO: Implement the ViewModel
    private val _postList = MutableLiveData<PostList>()
    val postList: LiveData<PostList> = _postList

    fun loadAllPosts() {
        CoroutineScope(Dispatchers.Main).launch {
            _postList.value = postMainRepository.loadAllPosts()
        }
    }
}