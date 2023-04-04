package com.example.hackernewsapp.network

import com.example.hackernewsapp.MyPreferences
import javax.inject.Inject

class MyPreferencesRepository @Inject constructor(private val myPreferences: MyPreferences, api: NetworkObject) {
}