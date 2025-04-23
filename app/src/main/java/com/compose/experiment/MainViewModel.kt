package com.compose.experiment


//import com.compose.experiment.presentations.local_search.Todo
//import com.compose.experiment.presentations.local_search.TodoListState
//import com.compose.experiment.presentations.local_search.TodoSearchManager
import android.content.Context
import android.content.pm.PackageManager
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.compose.experiment.data.ApiResult
import com.compose.experiment.model.User
import com.compose.experiment.pagination.repository.UserRepository
import com.compose.experiment.presentations.snackbars.SnackbarAction
import com.compose.experiment.presentations.snackbars.SnackbarController
import com.compose.experiment.presentations.snackbars.SnackbarEvent
import com.compose.experiment.presentations.wrapper.WrapperRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: UserRepository,
    private val wrapperRepository: WrapperRepository = WrapperRepository(),
    private val notificationBuilder: NotificationCompat.Builder,
    private val notificationManager: NotificationManagerCompat,
//    private val todoSearchManager: TodoSearchManager,
    @ApplicationContext private val context: Context,
) : ViewModel() {


    val isLoading: MutableState<Boolean> = mutableStateOf(false)
    val error: MutableState<String?> = mutableStateOf(null)
    // var data = mutableStateListOf<String>()

    private val _uiState = MutableStateFlow<ApiResult<*>>(ApiResult.Loading)
    val uiState: StateFlow<ApiResult<*>>
        get() = _uiState

    val data = wrapperRepository.fetchDataWithWrapper()


    var isLoggedIn by mutableStateOf(false)
        private set


//    var state by mutableStateOf(TodoListState())
//        private set // With a private setter, the state can be accessed or read in the UI but can only be modified in the view model.
//
//    private var searchJob: Job? = null
//
//    val generateToDoList = (1..100).map {
//        Todo(
//            namespace = "my_todos",
//            id = UUID.randomUUID().toString(),
//            score = 1,
//            title = "Todo $it",
//            text = "Description $it",
//            isDone = Random.nextBoolean(),
//        )
//    }// Initialize the todo search manager.

    init {
//        viewModelScope.launch {
//            todoSearchManager.init()
//
//            todoSearchManager.putTodos(generateToDoList) // Add the generated todos to the manager.
//            state =
//                state.copy(todos = generateToDoList) // Set the initial state with the default todos
//        }
    }

//    fun onSearchQueryChange(query: String) {
//        state = state.copy(searchQuery = query) // Update the state's search query.
//
//        searchJob?.cancel() // Cancel any ongoing search job.
//
//        searchJob = viewModelScope.launch {
//            delay(500L) // Add a delay to debounce the search input.
//            if (query.isBlank()) {
//                state = state.copy(todos = generateToDoList)
//            } else {
//                val todos = todoSearchManager.searchTodos(query)
//                state = state.copy(todos = todos)
//            }
//        }
//    }

//    fun onDoneChange(todo: Todo, isDone: Boolean) {
//        viewModelScope.launch {
//            // Update the todos 'isDone' status in the manager.
//            todoSearchManager.putTodos(listOf(todo.copy(isDone = isDone)))
//
//            // Update the state with the modified todo.
//            state = state.copy(
//                todos = state.todos.map {
//                    if (it.id == todo.id) {
//                        it.copy(isDone = isDone)
//                    } else {
//                        it
//                    }
//                }
//            )
//        }
//    }

    override fun onCleared() {
       // todoSearchManager.closeSession()
        super.onCleared()
    }


    fun showSnackBar(item : Int , actionPressed : () -> Unit = {}){
        viewModelScope.launch {
            SnackbarController.sendEvent(
                event = SnackbarEvent(
                    message = "Clicked from ViewModel Item is $item",
                    action = SnackbarAction(
                        name = "Click me",
                        action = {
                            SnackbarController.sendEvent(
                                event = SnackbarEvent(
                                    message = "Action pressed"
                                )
                            )
                            actionPressed()
                        }
                    ),

                )
            )
        }
    }

    fun showSimpleNotification() {
        if (ActivityCompat.checkSelfPermission(
                context,
                android.Manifest.permission.POST_NOTIFICATIONS
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return
        }
        notificationManager.notify(1, notificationBuilder.build())
    }

    fun updateSimpleNotification() {
        if (ActivityCompat.checkSelfPermission(
                context,
                android.Manifest.permission.POST_NOTIFICATIONS
            ) != PackageManager.PERMISSION_GRANTED
        ) {
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

sealed interface NavigationEvent {
    data object NavigateToProfile : NavigationEvent
}

data class LoginState(
    val isLoading: Boolean = false,
    val isLoggedIn: Boolean = false
)