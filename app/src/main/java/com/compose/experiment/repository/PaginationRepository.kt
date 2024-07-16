package com.compose.experiment.repository

import com.compose.experiment.model.ListItem
import kotlinx.coroutines.delay

// Repository to provide paginated data. Simulates a remote data source.
class PaginationRepository {

    private val remoteDataSource = (1..100).map {
        ListItem(
            title = "Item $it",
            description = "Description $it"
        )
    }

    // Simulate a network request to fetch items.
    suspend fun getItems(page: Int, pageSize: Int): Result<List<ListItem>> {
        delay(2000L) // Simulate network delay.
        val startingIndex = page * pageSize

        // Return a sublist or an empty list if the end of the data source is reached.
        return if (startingIndex + pageSize <= remoteDataSource.size) {
            Result.success(
                remoteDataSource.slice(startingIndex until startingIndex + pageSize)
            )
        } else Result.success(emptyList())
    }
}


