package com.compose.experiment.presentations.pagination.pagination

// Defines a Paginator interface with methods for
// loading the next set of items and resetting the paginator.
interface Paginator<Key, Item> {
    suspend fun loadNextItems() // Loads the next set of items.
    fun reset() // Resets the paginator to its initial state.
}