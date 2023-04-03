package com.example.hackernewsapp.network

import com.example.hackernewsapp.model.IdModel
import com.example.hackernewsapp.model.StoryModel
import javax.inject.Inject

class StoryRepository @Inject constructor(private val api: NetworkObject) {
    suspend fun getNewStoriesList(): List<StoryModel> {
        val list: MutableList<StoryModel>
        val listOfIds = mutableListOf<IdModel>()
        api.service.listNewStories().body()?.let { IdModel(it.take(20)) }?.let { listOfIds.add(it) }
        list = listOfIds[0].ids.map { id ->
            api.service.getItemFromId(id).body()?.toStoryDomain() ?: emptyList<StoryModel>()
        } as MutableList<StoryModel>
        return list
    }

    suspend fun getTopStoriesList(): List<StoryModel> {
        val list: MutableList<StoryModel>
        val listOfIds = mutableListOf<IdModel>()
        api.service.listTopStories().body()?.let { IdModel(it.take(12)) }?.let { listOfIds.add(it) }
        list = listOfIds[0].ids.map { id ->
            api.service.getItemFromId(id).body()?.toStoryDomain() ?: emptyList<StoryModel>()
        } as MutableList<StoryModel>
        return list
    }

    suspend fun getBestStoriesList(): List<StoryModel> {
        val list: MutableList<StoryModel>
        val listOfIds = mutableListOf<IdModel>()
        api.service.listBestStories().body()?.let { IdModel(it.take(20)) }?.let { listOfIds.add(it) }
        list = listOfIds[0].ids.map { id ->
            api.service.getItemFromId(id).body()?.toStoryDomain() ?: emptyList<StoryModel>()
        } as MutableList<StoryModel>
        return list
    }
}