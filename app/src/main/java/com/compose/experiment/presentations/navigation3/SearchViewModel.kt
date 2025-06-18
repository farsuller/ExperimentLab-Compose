package com.compose.experiment.presentations.navigation3

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class SearchViewModel : ViewModel() {

    var number2 by mutableIntStateOf(0)
        private set


    fun incrementNumber() {
        number2++
    }

    init {
        println("ViewModel Init")
    }

    override fun onCleared() {
        super.onCleared()
        println("ViewModel Cleared")
    }
}