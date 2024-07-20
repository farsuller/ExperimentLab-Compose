package com.compose.experiment.presentations.local_search

data class TodoListState(
    val todos: List<Todo> = emptyList(),
    val searchQuery: String = "",
)
