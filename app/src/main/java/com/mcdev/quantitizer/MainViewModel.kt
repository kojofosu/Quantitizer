package com.mcdev.quantitizer

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel:ViewModel() {
    val numberYouPiked = MutableLiveData<Int>()
}