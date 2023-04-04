package com.example.hackernewsapp.network

import com.example.hackernewsapp.MyPreferences
import com.example.hackernewsapp.model.StoryModel
import javax.inject.Inject

class MyPreferencesRepository @Inject constructor(private val myPreferences: MyPreferences, private val networkObject: NetworkObject) {

    fun getListOfFavourite(): List<Int>? {
        return if(myPreferences.getFavouriteList().isNullOrEmpty()){
            emptyList<Int>()
        } else {
            myPreferences.getFavouriteList()
        }
    }

    suspend fun getStoryList(): List<StoryModel>{
        val listOfStoryModel : MutableList<StoryModel> = getListOfFavourite()?.map { id ->
            networkObject.service.getItemFromId(id).body()?.toStoryDomain() ?: emptyList<StoryModel>()
        } as MutableList<StoryModel>
        return listOfStoryModel
    }
}