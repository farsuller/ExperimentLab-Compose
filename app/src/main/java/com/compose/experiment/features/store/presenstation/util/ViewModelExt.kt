package com.compose.experiment.features.store.presenstation.util

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.compose.experiment.utils.EventBus
import kotlinx.coroutines.launch

fun ViewModel.sendEvent(event : Any){
    viewModelScope.launch {
        EventBus.sendEvent(event)
    }
}