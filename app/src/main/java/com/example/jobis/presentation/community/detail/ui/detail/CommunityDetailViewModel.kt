package com.example.jobis.presentation.community.detail.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.jobis.data.repository.CommunityRepository
import com.example.jobis.data.response.PostResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CommunityDetailViewModel(private val communityRepository: CommunityRepository) : ViewModel() {
    private val _post = MutableLiveData<PostResponse>()
    val post: LiveData<PostResponse> = _post

    fun loadPost(id: String) {
        CoroutineScope(Dispatchers.Main).launch {
            _post.value = communityRepository.loadPostDetail(id)
        }
    }
}