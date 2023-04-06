package com.example.hackernewsapp.utils

import com.example.hackernewsapp.model.CommentModel

sealed class CommentListResult{
    data class Success(val comments: List<CommentModel>): CommentListResult()
    data class Error(val e: java.lang.Exception): CommentListResult()
}
