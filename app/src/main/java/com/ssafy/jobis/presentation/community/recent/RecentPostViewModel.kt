package com.ssafy.jobis.presentation.community.recent

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ssafy.jobis.data.repository.CommunityRepository
import com.ssafy.jobis.data.response.PostResponseList
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RecentPostViewModel(private val communityRepository: CommunityRepository) : ViewModel() {
    private val _recentPostList = MutableLiveData<PostResponseList>()
    val recentPostList: LiveData<PostResponseList> = _recentPostList

    fun loadRecentPosts() {
        CoroutineScope(Dispatchers.Main).launch {
            _recentPostList.value = communityRepository.loadRecentPosts()
        }
    }

    fun filterPost(pos: Int) {

    }
}