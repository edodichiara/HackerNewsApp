package com.example.hackernewsapp

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class MyPreferences @Inject constructor(@ApplicationContext context: Context) {
    private val LISTOFFAVOURITE = "listOfFavourite"

    private val preferenceListOfFavourite: SharedPreferences =
        context.getSharedPreferences(LISTOFFAVOURITE, Context.MODE_PRIVATE)
    private val gson = GsonBuilder().create()

    fun saveFavouriteList(list: List<Int>){
        val stringToSave = gson.toJson(list)
        preferenceListOfFavourite.edit().putString(LISTOFFAVOURITE, stringToSave).apply()
    }

    fun getFavouriteList(): List<Int>?{
        val json = preferenceListOfFavourite.getString(LISTOFFAVOURITE, null)
        val itemType = object : TypeToken<List<Int>>() {}.type
        val list = gson.fromJson<List<Int>>(json, itemType)
        return json.let { list }
    }
}