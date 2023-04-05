package com.example.hackernewsapp.network

import com.example.hackernewsapp.MyPreferences
import com.example.hackernewsapp.model.IdModel
import com.example.hackernewsapp.model.StoryModel
import javax.inject.Inject

/**
 * @author Edoardo Di Chiara
 */
class StoryRepository @Inject constructor(
    private val api: NetworkObject,
    private val myPreferences: MyPreferences
) {
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
        api.service.listBestStories().body()?.let { IdModel(it.take(20)) }
            ?.let { listOfIds.add(it) }
        list = listOfIds[0].ids.map { id ->
            api.service.getItemFromId(id).body()?.toStoryDomain() ?: emptyList<StoryModel>()
        } as MutableList<StoryModel>
        return list
    }

    fun savePreference(id: Int) {
        val list: MutableList<Int> = (if (myPreferences.getFavouriteList().isNullOrEmpty()) {
            emptyList()
        } else {
            myPreferences.getFavouriteList()
        })?.toMutableList() ?: mutableListOf()
        list.add(id)
        myPreferences.saveFavouriteList(list.distinct())
    }

    fun deletePreference(id: Int) {
        val list: MutableList<Int> = (if (myPreferences.getFavouriteList().isNullOrEmpty()) {
            emptyList()
        } else {
            myPreferences.getFavouriteList()
        })?.toMutableList() ?: mutableListOf()
        list.remove(id)
        myPreferences.saveFavouriteList(list)
    }

    fun getListOfFavourite(): List<Int>? {
        return if (myPreferences.getFavouriteList().isNullOrEmpty()) {
            emptyList<Int>()
        } else {
            myPreferences.getFavouriteList()
        }
    }
}