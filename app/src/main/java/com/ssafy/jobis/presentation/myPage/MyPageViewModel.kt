package com.ssafy.jobis.presentation.myPage

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ssafy.jobis.data.repository.MyPageRepository
import com.ssafy.jobis.data.response.PostResponseList
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MyPageViewModel(private val myPageRepository: MyPageRepository): ViewModel() {

    private val _myLikeList = MutableLiveData<PostResponseList>()
    val myLikeList: LiveData<PostResponseList> = _myLikeList

    private val _myPostList = MutableLiveData<PostResponseList>()
    val myPostList: LiveData<PostResponseList> = _myPostList

    private val _myCommentList = MutableLiveData<PostResponseList>()
    val myCommentList: LiveData<PostResponseList> = _myCommentList

    fun loadMyLikeList(uid: String) {
        CoroutineScope(Dispatchers.Main).launch {
            val res = myPageRepository.loadMyLikePosts(uid)
        }
    }

    fun loadMyPostList(uid: String) {
        CoroutineScope(Dispatchers.Main).launch {
            val res = myPageRepository.loadMyPosts(uid)
        }
    }

    fun loadMyCommentList(uid: String) {
        CoroutineScope(Dispatchers.Main).launch {
            val res = myPageRepository.loadMyCommentPosts(uid)
        }
    }
}