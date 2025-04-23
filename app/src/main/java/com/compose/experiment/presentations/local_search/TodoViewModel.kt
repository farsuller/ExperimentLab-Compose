//package com.compose.experiment.presentations.local_search
//
//import androidx.compose.runtime.getValue
//import androidx.compose.runtime.mutableStateOf
//import androidx.compose.runtime.setValue
//import androidx.lifecycle.ViewModel
//import androidx.lifecycle.viewModelScope
//import kotlinx.coroutines.Job
//import kotlinx.coroutines.delay
//import kotlinx.coroutines.launch
//import java.util.UUID
//import kotlin.random.Random
//
//class TodoViewModel(
//    private val todoSearchManager: TodoSearchManager
//) : ViewModel() {
//
//    var state by mutableStateOf(TodoListState())
//        private set // With a private setter, the state can be accessed or read in the UI but can only be modified in the view model.
//
//    private var searchJob: Job? = null
//
//    init {
//        viewModelScope.launch {
//            todoSearchManager.init() // Initialize the todo search manager.
//
//            // Generate a list of 100 Todo items with random 'isDone' values.
//            val todos = (1..100).map {
//                Todo(
//                    namespace = "my_todos",
//                    id = UUID.randomUUID().toString(),
//                    score = 1,
//                    title = "Todo $it",
//                    text = "Description $it",
//                    isDone = Random.nextBoolean(),
//                )
//            }
//            todoSearchManager.putTodos(todos) // Add the generated todos to the manager.
//        }
//    }
//
//    fun onSearchQueryChange(query: String) {
//        state = state.copy(searchQuery = query) // Update the state's search query.
//
//        searchJob?.cancel() // Cancel any ongoing search job.
//
//        searchJob = viewModelScope.launch {
//            delay(500L) // Add a delay to debounce the search input.
//            val todos = todoSearchManager.searchTodos(query) // Search for todos matching the query.
//            state = state.copy(todos = todos) // Update the state with the search results.
//        }
//    }
//
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
//
//    override fun onCleared() {
//        todoSearchManager.closeSession()
//        super.onCleared()
//    }
//}