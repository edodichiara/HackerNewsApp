package com.example.hackernewsapp.ui.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hackernewsapp.network.StoryRepository
import com.example.hackernewsapp.utils.StoryListResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewStoriesFragmentViewModel @Inject constructor(private val storyRepository: StoryRepository) :
    ViewModel() {

    private var _newStoryListResult = MutableLiveData<StoryListResult>()
    val newStoryListResult: LiveData<StoryListResult> get() = _newStoryListResult

    private var _topStoryListResult = MutableLiveData<StoryListResult>()
    val topStoryListResult: LiveData<StoryListResult> get() = _topStoryListResult

    private var _bestStoryListResult = MutableLiveData<StoryListResult>()
    val bestStoryListResult: LiveData<StoryListResult> get() = _bestStoryListResult

    fun retrieveNewStories() {
        viewModelScope.launch(Dispatchers.Main) {
            try {
                _newStoryListResult.value =
                    StoryListResult.Success(storyRepository.getNewStoriesList())
                _bestStoryListResult.value =
                    StoryListResult.Success(storyRepository.getBestStoriesList())
                _topStoryListResult.value =
                    StoryListResult.Success(storyRepository.getTopStoriesList())
                Log.d("Retrieve stories", "retrieveStories: ${storyRepository.getNewStoriesList()}")
            } catch (e: java.lang.Exception) {
                _newStoryListResult.value = StoryListResult.Error(e)
                _topStoryListResult.value = StoryListResult.Error(e)
                _bestStoryListResult.value = StoryListResult.Error(e)
            }
        }
    }

    fun saveStoryOnMyFavourite(id: Int){
        storyRepository.savePreference(id)
    }

    fun deleteStoryFromMyFavourite(id: Int){
        storyRepository.deletePreference(id)
    }

    fun getStoriesFromMyFavourite(): List<Int> {
        return if (storyRepository.getListOfFavourite().isNullOrEmpty()){
            emptyList()
        } else {
            storyRepository.getListOfFavourite()
        }!!
    }
}