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

    fun loadAllPosts() {
        CoroutineScope(Dispatchers.Main).launch {
            _postList.value = communityRepository.loadAllPosts()
        }
    }
}