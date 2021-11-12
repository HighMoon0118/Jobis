package com.ssafy.jobis.presentation.myPage

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ssafy.jobis.data.model.community.Comment
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

    private val _myCommentList = MutableLiveData<MutableList<Comment>>()
    val myCommentList: LiveData<MutableList<Comment>> = _myCommentList

    fun loadMyLikeList(uid: String) {
        CoroutineScope(Dispatchers.Main).launch {
            val res = myPageRepository.loadMyLikePosts(uid)
            _myLikeList.value = res
        }
    }

    fun loadMyPostList(uid: String) {
        CoroutineScope(Dispatchers.Main).launch {
            val res = myPageRepository.loadMyPosts(uid)
            _myPostList.value = res
        }
    }

    fun loadMyCommentList(uid: String) {
        CoroutineScope(Dispatchers.Main).launch {
            val res = myPageRepository.loadMyCommentPosts(uid)
            _myCommentList.value = res
        }
    }
}