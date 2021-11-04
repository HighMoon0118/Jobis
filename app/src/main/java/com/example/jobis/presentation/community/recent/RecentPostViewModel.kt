package com.example.jobis.presentation.community.recent

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.jobis.data.repository.CommunityRepository
import com.example.jobis.presentation.community.PostList
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RecentPostViewModel(private val communityRepository: CommunityRepository) : ViewModel() {
    private val _recentPostList = MutableLiveData<PostList>()
    val recentPostList: LiveData<PostList> = _recentPostList

    fun loadRecentPosts() {
        CoroutineScope(Dispatchers.Main).launch {
            _recentPostList.value = communityRepository.loadRecentPosts()
        }
    }
}