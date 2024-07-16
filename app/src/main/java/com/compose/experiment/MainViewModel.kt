package com.compose.experiment


import android.content.Context
import android.content.pm.PackageManager
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat

import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.compose.experiment.data.ApiResult
import com.compose.experiment.model.User
import com.compose.experiment.presentations.wrapper.WrapperRepository
import com.compose.experiment.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: UserRepository,
    private val wrapperRepository: WrapperRepository = WrapperRepository(),
    private val notificationBuilder: NotificationCompat.Builder,
    private val notificationManager: NotificationManagerCompat,
    @ApplicationContext private val context: Context
) : ViewModel() {


    val isLoading: MutableState<Boolean> = mutableStateOf(false)
    val error: MutableState<String?> = mutableStateOf(null)
    // var data = mutableStateListOf<String>()

    private val _uiState = MutableStateFlow<ApiResult<*>>(ApiResult.Loading)
    val uiState: StateFlow<ApiResult<*>>
        get() = _uiState

    val data = wrapperRepository.fetchDataWithWrapper()

    // Create a channel for navigation events, which can be used to send and receive events.
    private val navigationChannel = Channel<NavigationEvent>()

    // Convert the navigation channel into a Flow for observing navigation events.
    val navigationEventsChannelFlow = navigationChannel.receiveAsFlow()

    // Create a MutableSharedFlow with a buffer that can replay the last 3 events.
    private val _navigationEvents = MutableSharedFlow<NavigationEvent>(replay = 3)

    // Expose the shared flow as a read-only SharedFlow for external observers.
    val navigationSharedFlow = _navigationEvents.asSharedFlow()


    var isLoggedIn by mutableStateOf(false)
        private set

    var state by mutableStateOf(LoginState())
        private set


    fun login(){
        viewModelScope.launch {
            state = state.copy(isLoading = true)
            delay(3000L)

            //navigationChannel.send(NavigationEvent.NavigateToProfile)
            _navigationEvents.emit(NavigationEvent.NavigateToProfile)

            state = state.copy(
                isLoading = false,
            )
        }
    }

    fun showSimpleNotification() {
        if (ActivityCompat.checkSelfPermission(context, android.Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
            return
        }
        notificationManager.notify(1, notificationBuilder.build())
    }

    fun updateSimpleNotification() {
        if (ActivityCompat.checkSelfPermission(context, android.Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
            return
        }
        notificationManager.notify(
            1, notificationBuilder
                .setContentTitle("NEW TITLE")
                .build()
        )
    }

    fun cancelSimpleNotification() {
        notificationManager.cancel(1)
    }

    fun deleteById(id: Int) {
        viewModelScope.launch {
            repository.deleteById(id = id).collectLatest { data ->
                _uiState.update { data }
            }
        }
    }

    fun insert(user: User) {
        viewModelScope.launch {
            repository.insert(user = user).collectLatest { data ->
                _uiState.update { data }
            }
        }
    }

    fun updateById(id: Int, user: User) {
        viewModelScope.launch {
            repository.updateById(id = id, user = user).collectLatest { data ->
                _uiState.update { data }
            }
        }
    }

    fun findAll() {
        viewModelScope.launch {
            repository.findAll().collectLatest { data ->
                _uiState.update { data }
            }
        }
    }

    fun menuAll() {
        viewModelScope.launch {
            repository.menuAll().collectLatest { data ->
                _uiState.update { data }
            }
        }
    }
}

sealed interface NavigationEvent{
    data object NavigateToProfile: NavigationEvent
}

data class LoginState(
    val isLoading:Boolean = false,
    val isLoggedIn:Boolean = false
)