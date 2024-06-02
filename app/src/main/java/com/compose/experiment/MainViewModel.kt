package com.compose.experiment


import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.compose.experiment.data.ApiResult
import com.compose.experiment.model.User
import com.compose.experiment.repository.UserRepository
import com.compose.experiment.presentations.wrapper.WrapperRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: UserRepository,
    private val wrapperRepository: WrapperRepository = WrapperRepository()
) : ViewModel() {


    val isLoading: MutableState<Boolean> = mutableStateOf(false)
    val error: MutableState<String?> = mutableStateOf(null)
   // var data = mutableStateListOf<String>()

    val data = wrapperRepository.fetchDataWithWrapper()

    init {
//        isLoading.value = true
//        viewModelScope.launch {
//            delay(2000)
//            isLoading.value = false
//            wrapperRepository.fetchData().catch {
//                error.value = it.message.toString()
//            }.collectLatest {
//                data.addAll(it)
//            }
//        }
    }

    private val _uiState = MutableStateFlow<ApiResult<*>>(ApiResult.Loading)
    val uiState: StateFlow<ApiResult<*>>
        get() = _uiState


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