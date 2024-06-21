package com.compose.experiment.presentations.pagination

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.compose.experiment.pagination.DefaultPaginator
import com.compose.experiment.repository.PaginationRepository
import kotlinx.coroutines.launch

class PaginationViewModel : ViewModel() {

    private val repository = PaginationRepository()
    var state by mutableStateOf(ScreenState()) // State to track UI state including loading, items, error, and pagination info.

    private val paginator = DefaultPaginator(
        initialKey = state.page, // Start pagination from the current page in the state.
        onLoadUpdated = {
            state = state.copy(isLoading = it) // Update loading state.
        },
        onRequest = { nextPage ->
            repository.getItems(nextPage, 20) // Request the next set of items.
        },
        getNextKey = {
            state.page + 1 // Increment the page number for the next request.
        },
        onError = {
            state = state.copy(error = it?.localizedMessage) // Update error state.
        },
        onSuccess = { items, newKey ->
            state = state.copy(
                items = state.items + items, // Append new items to the existing list.
                page = newKey, // Update the current page.
                endReached = items.isEmpty() // Check if the end of the list has been reached.
            )
        }
    )

    init {
        loadNextItems() // Load the initial set of items when the ViewModel is created.
    }

    fun loadNextItems() {
        viewModelScope.launch {
            paginator.loadNextItems() // Load the next set of items.
        }
    }
}