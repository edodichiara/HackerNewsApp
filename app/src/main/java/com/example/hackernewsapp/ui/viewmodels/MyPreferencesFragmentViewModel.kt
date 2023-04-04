package com.example.hackernewsapp.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hackernewsapp.network.MyPreferencesRepository
import com.example.hackernewsapp.utils.StoryListResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MyPreferencesFragmentViewModel @Inject constructor(private val myPreferencesRepository: MyPreferencesRepository): ViewModel(){

    private var _newStoryListResult = MutableLiveData<StoryListResult>()
    val newStoryListResult: LiveData<StoryListResult> get() = _newStoryListResult

    fun retrieveNewStories() {
        viewModelScope.launch(Dispatchers.Main) {
            try {
                _newStoryListResult.value =
                    StoryListResult.Success(myPreferencesRepository.getStoryList())

            } catch (e: java.lang.Exception) {
                _newStoryListResult.value = StoryListResult.Error(e)
            }
        }
    }

    fun getStoriesFromMyFavourite(): List<Int> {
        return if (myPreferencesRepository.getListOfFavourite().isNullOrEmpty()){
            emptyList()
        } else {
            myPreferencesRepository.getListOfFavourite()
        }!!
    }

    fun saveStoryOnMyFavourite(id: Int){
        myPreferencesRepository.savePreference(id)
    }

    fun deleteStoryFromMyFavourite(id: Int){
        myPreferencesRepository.deletePreference(id)
    }
}