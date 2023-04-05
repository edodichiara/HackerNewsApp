package com.example.hackernewsapp.utils

import com.example.hackernewsapp.model.StoryModel

/**
 * @author Edoardo Di Chiara
 */
sealed class StoryListResult{
    data class Success(val data: List<StoryModel>): StoryListResult()
    data class Error(val e: java.lang.Exception): StoryListResult()
}