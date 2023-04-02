package com.example.hackernewsapp.model

import java.util.Date

data class StoryModel(
    val author: String,
    val totalCommentsCount: Int,
    val id: Int,
    val idComments: List<Int>,
    val score: Int,
    val time: Date,
    val title: String,
    val type: String,
    val url: String
)
