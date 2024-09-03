package com.compose.experiment.observable

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.compose.experiment.LoginState
import com.compose.experiment.NavigationEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ObservableViewModel @Inject constructor() : ViewModel() {
    // Create a channel for navigation events, which can be used to send and receive events.
    private val navigationChannel = Channel<NavigationEvent>()

    // Convert the navigation channel into a Flow for observing navigation events.
    val navigationEventsChannelFlow = navigationChannel.receiveAsFlow()

    // Create a MutableSharedFlow with a buffer that can replay the last 3 events.
    private val _navigationEvents = MutableSharedFlow<NavigationEvent>(replay = 3)

    // Expose the shared flow as a read-only SharedFlow for external observers.
    val navigationSharedFlow = _navigationEvents.asSharedFlow()

    var loginState by mutableStateOf(LoginState())
        private set

    fun login() {
        viewModelScope.launch {
            loginState = loginState.copy(isLoading = true)
            delay(3000L)

            //navigationChannel.send(NavigationEvent.NavigateToProfile)
            _navigationEvents.emit(NavigationEvent.NavigateToProfile)

            loginState = loginState.copy(
                isLoading = false,
            )
        }
    }
}