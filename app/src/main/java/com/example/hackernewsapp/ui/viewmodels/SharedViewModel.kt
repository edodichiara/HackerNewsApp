package com.example.hackernewsapp.ui.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

/**
 * @author Edoardo Di Chiara
 */
class SharedViewModel : ViewModel() {
    val selectedId = MutableLiveData<Int>()
}