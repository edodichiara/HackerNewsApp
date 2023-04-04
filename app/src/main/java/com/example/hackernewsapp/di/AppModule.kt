package com.example.hackernewsapp.di

import android.content.Context
import com.example.hackernewsapp.MyPreferences
import com.example.hackernewsapp.network.MyPreferencesRepository
import com.example.hackernewsapp.network.NetworkObject
import com.example.hackernewsapp.network.StoryRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
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
    fun provideStoryRepository(networkObject: NetworkObject, preferences: MyPreferences): StoryRepository {
        return StoryRepository(networkObject, preferences)
    }

    @Singleton
    @Provides
    fun providePreferencesRepository(networkObject: NetworkObject, preferences: MyPreferences): MyPreferencesRepository {
        return MyPreferencesRepository(preferences, networkObject)
    }


    @Singleton
    @Provides
    fun provideMyPreference(@ApplicationContext context: Context): MyPreferences{
        return MyPreferences(context)
    }
}