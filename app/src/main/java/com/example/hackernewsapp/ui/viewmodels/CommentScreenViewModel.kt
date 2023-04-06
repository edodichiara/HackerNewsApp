package com.example.hackernewsapp.ui.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hackernewsapp.network.CommentRepository
import com.example.hackernewsapp.utils.CommentListResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CommentScreenViewModel @Inject constructor(private val commentRepository: CommentRepository): ViewModel() {
    private var _commentListResult = MutableLiveData<CommentListResult>()
    val commentListResult : LiveData<CommentListResult> get() = _commentListResult

    fun retrieveRepo(id: Int) {
        viewModelScope.launch(Dispatchers.Main) {
            try {
                _commentListResult.value = CommentListResult.Success(commentRepository.getListOfCommentFromId(id))
            } catch (e: java.lang.Exception){
                _commentListResult.value = CommentListResult.Error(e)
            }
        }
    }
}