package com.example.hackernewsapp.di

import com.example.hackernewsapp.network.NetworkObject
import com.example.hackernewsapp.network.StoryRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Singleton
    @Provides
    fun provideNetworkObject(): NetworkObject = NetworkObject

    @Singleton
    @Provides
    fun provideStoryRepository(networkObject: NetworkObject): StoryRepository {
        return StoryRepository(networkObject)
    }
}