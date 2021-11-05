package com.example.jobis.presentation.community.popular

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.jobis.data.repository.CommunityRepository
import com.example.jobis.data.response.PostResponseList
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PopularPostViewModel(private val communityRepository: CommunityRepository) : ViewModel() {
    private val _popularPostList = MutableLiveData<PostResponseList>()
    val popularPostList: LiveData<PostResponseList> = _popularPostList

    fun loadPopularPosts() {
        CoroutineScope(Dispatchers.Main).launch {
            _popularPostList.value = communityRepository.loadPopularPosts()
        }
    }
}