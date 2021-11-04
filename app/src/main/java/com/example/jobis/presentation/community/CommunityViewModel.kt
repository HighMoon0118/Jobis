package com.example.jobis.presentation.community

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.jobis.data.repository.CommunityRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CommunityViewModel(private val communityRepository: CommunityRepository) : ViewModel() {
    private val _postList = MutableLiveData<PostList>()
    val postList: LiveData<PostList> = _postList
    private val _recentPostList = MutableLiveData<PostList>()
    val recentPostList: LiveData<PostList> = _recentPostList
    private val _popularPostList = MutableLiveData<PostList>()
    val popularPostList: LiveData<PostList> = _popularPostList

    fun loadAllPosts() {
        CoroutineScope(Dispatchers.Main).launch {
            _postList.value = communityRepository.loadAllPosts()
        }
    }

    fun loadRecentPosts() {
        CoroutineScope(Dispatchers.Main).launch {
            _recentPostList.value = communityRepository.loadRecentPosts()
        }
    }

    fun loadPopularPosts() {
        CoroutineScope(Dispatchers.Main).launch {
            _popularPostList.value = communityRepository.loadPopularPosts()
        }
    }
}