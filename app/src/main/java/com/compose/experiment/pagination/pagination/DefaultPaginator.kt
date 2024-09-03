package com.compose.experiment.pagination.pagination

// Default implementation of the Paginator interface.
class DefaultPaginator<Key, Item>(
    private val initialKey: Key, // The initial key to start pagination.
    private inline val onLoadUpdated: (Boolean) -> Unit, // Callback to update loading state.
    private inline val onRequest: suspend (nextKey: Key) -> Result<List<Item>>, // Request function to load items based on the current key.
    private inline val getNextKey: suspend (List<Item>) -> Key, // Function to determine the next key based on the items loaded.
    private inline val onError: suspend (Throwable?) -> Unit, // Callback to handle errors.
    private inline val onSuccess: suspend (items: List<Item>, newKey: Key) -> Unit // Callback to handle success, updating the state with new items and key.
) : Paginator<Key, Item> {

    private var currentKey = initialKey // Tracks the current key for pagination.
    private var isMakingRequest = false // Flag to prevent multiple concurrent requests.

    override suspend fun loadNextItems() {
        if (isMakingRequest) {
            return // If a request is already in progress, return early.
        }
        isMakingRequest = true
        onLoadUpdated(true) // Notify that loading has started.

        // Request the next set of items.
        val result = onRequest(currentKey)
        isMakingRequest = false
        val items = result.getOrElse {
            onError(it) // Handle error if the request fails.
            onLoadUpdated(false) // Notify that loading has ended.
            return
        }
        currentKey = getNextKey(items) // Update the current key based on the loaded items.
        onSuccess(items, currentKey) // Handle success, updating the state with new items and key.
        onLoadUpdated(false) // Notify that loading has ended.
    }

    override fun reset() {
        currentKey = initialKey // Reset the paginator to the initial key.
    }
}