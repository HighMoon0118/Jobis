package com.example.jobis.presentation.community.popular

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.jobis.data.repository.CommunityRepository
import com.example.jobis.presentation.community.PostList
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PopularPostViewModel(private val communityRepository: CommunityRepository) : ViewModel() {
    private val _popularPostList = MutableLiveData<PostList>()
    val popularPostList: LiveData<PostList> = _popularPostList

    fun loadPopularPosts() {
        CoroutineScope(Dispatchers.Main).launch {
            _popularPostList.value = communityRepository.loadPopularPosts()
        }
    }
}