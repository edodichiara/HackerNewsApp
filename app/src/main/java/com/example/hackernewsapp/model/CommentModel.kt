package com.example.hackernewsapp.model

import java.util.Date

data class CommentModel(
    val id: Int,
    val by: String,
    val subComment: List<Int>,
    val score: Int,
    val parent: Int,
    val text: String,
    val time: Date,
    val type: String
)
