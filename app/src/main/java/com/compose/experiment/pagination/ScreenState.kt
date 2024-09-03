package com.compose.experiment.pagination

import com.compose.experiment.pagination.model.ListItem

// Data class to represent the UI state including loading state,
// items, error message, pagination info, etc.
data class ScreenState(
    val isLoading : Boolean = false,
    val items : List<ListItem> = emptyList(),
    val error : String? = null,
    val endReached : Boolean = false,
    val page : Int = 0
)
