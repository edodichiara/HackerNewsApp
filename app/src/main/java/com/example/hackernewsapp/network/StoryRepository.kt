package com.example.hackernewsapp.network

import android.util.Log
import com.example.hackernewsapp.model.StoryModel
import javax.inject.Inject

class StoryRepository @Inject constructor(private val api: NetworkObject) {


    suspend fun getNewStoriesList(): List<StoryModel> {
        val list = mutableListOf<StoryModel>()
        Log.d("Retrieve stories", "apiResponde: ")

        api.service.listNewStories().body()?.map { id ->
            Log.d("Retrieve stories", "id: ${id}")
            api.service.getItemFromId(id).body()?.toStoryDomain()?.let { list.add(it) }
        }

        return list
    }
}