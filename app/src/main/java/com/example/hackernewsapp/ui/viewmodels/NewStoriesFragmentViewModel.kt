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
    private var _storyListResult = MutableLiveData<StoryListResult>()
    val storyListResult: LiveData<StoryListResult> get() = _storyListResult

    fun retrieveStories() {
        viewModelScope.launch(Dispatchers.Main) {
            try {
                _storyListResult.value =
                    StoryListResult.Success(storyRepository.getNewStoriesList())
                Log.d("Retrieve stories", "retrieveStories: ${storyRepository.getNewStoriesList().first()}")
            } catch (e: java.lang.Exception) {
                _storyListResult.value = StoryListResult.Error(e)
                Log.d("Retrieve stories", "retrieveStories: ${e}")

            }
        }
    }
}