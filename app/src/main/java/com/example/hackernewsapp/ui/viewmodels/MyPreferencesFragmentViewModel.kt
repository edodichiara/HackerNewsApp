package com.example.hackernewsapp.ui.viewmodels

import androidx.lifecycle.ViewModel
import com.example.hackernewsapp.network.MyPreferencesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MyPreferencesFragmentViewModel @Inject constructor(private val myPreferencesRepository: MyPreferencesRepository): ViewModel(){
}