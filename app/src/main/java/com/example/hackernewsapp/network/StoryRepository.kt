package com.example.hackernewsapp.network

import android.util.Log
import com.example.hackernewsapp.model.IdModel
import com.example.hackernewsapp.model.StoryModel
import javax.inject.Inject

class StoryRepository @Inject constructor(private val api: NetworkObject) {


    suspend fun getNewStoriesList(): List<StoryModel> {
        var list: MutableList<StoryModel> = mutableListOf()
        val listOfIds = mutableListOf<IdModel>()
        api.service.listNewStories().body()?.let { IdModel(it.take(10)) }?.let { listOfIds.add(it) }
        list = listOfIds.get(0).ids.map { id ->
            api.service.getItemFromId(id).body()?.toStoryDomain() ?: emptyList<StoryModel>()
        } as MutableList<StoryModel>
        return list
    }
}