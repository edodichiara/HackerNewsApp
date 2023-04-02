package com.example.hackernewsapp.network

import com.example.hackernewsapp.network.dto.IdListDTO
import com.example.hackernewsapp.network.dto.ItemDTO
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface HackerNewsService {
    @GET("newstories.json")
    suspend fun listNewStories(): Response<IdListDTO>

    @GET("topstories.json")
    suspend fun listTopStories(): Response<IdListDTO>

    @GET("beststories.json")
    suspend fun listBestStories(): Response<IdListDTO>

    @GET("item/{id}.json")
    suspend fun getItemFromId(@Path ("id") id: Int): Response<ItemDTO>
}