package com.ssafy.jobis.presentation.community.detail.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ssafy.jobis.data.repository.CommunityRepository
import com.ssafy.jobis.data.response.PostResponse
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