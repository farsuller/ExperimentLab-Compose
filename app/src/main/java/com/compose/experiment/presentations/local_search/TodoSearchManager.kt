package com.compose.experiment.presentations.local_search

import android.content.Context
import androidx.appsearch.app.AppSearchSession
import androidx.appsearch.app.PutDocumentsRequest
import androidx.appsearch.app.SearchSpec
import androidx.appsearch.app.SetSchemaRequest
import androidx.appsearch.localstorage.LocalStorage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class TodoSearchManager
@Inject constructor(private val context: Context) {

    private var session: AppSearchSession? = null

    suspend fun init() {
        withContext(Dispatchers.IO) {
            // Create an AppSearch session for local storage.
            // LocalStorage.createSearchSessionAsync will return null if the operation was not successful.
            val sessionFuture = LocalStorage.createSearchSessionAsync(
                LocalStorage.SearchContext.Builder(context, DATABASE_NAME).build()
            )

            // Define the schema for the Todo document class.
            val setSchemaRequest = SetSchemaRequest.Builder()
                .addDocumentClasses(Todo::class.java)
                .build()


            session = sessionFuture.get() // Retrieve the session.

            // Set the schema for the session.
            session?.setSchemaAsync(setSchemaRequest)

        }
    }

    suspend fun putTodos(todos: List<Todo>): Boolean {
        return withContext(Dispatchers.IO) {
            // Add a list of Todo documents to the session.
            // putAsync will return null if the operation was not successful.
            session?.putAsync(
                PutDocumentsRequest.Builder()
                    .addDocuments(todos)
                    .build()
            )?.get()?.isSuccess == true // Check if the operation was successful.
        }
    }

    suspend fun searchTodos(query: String): List<Todo> {
        return withContext(Dispatchers.IO) {
            // Create a search specification.
            // Set the snippet count to 10, this will return the first 10 results.
            // Add a filter namespace to the search specification. This will filter out results that do not belong to the namespace.
            // Set the ranking strategy to usage count, this will return the results with the highest usage count.

            val searchSpecBuilder = SearchSpec.Builder()
                .addFilterNamespaces("my_todos")
                .setRankingStrategy(SearchSpec.RANKING_STRATEGY_USAGE_COUNT)

            if (query.isNotBlank()) {
                searchSpecBuilder.setSnippetCount(10)
            }

            val searchSpec = searchSpecBuilder.build()

            // Perform the search query in the session.
            val result = session?.search(query, searchSpec) ?: return@withContext emptyList()

            // Retrieve the first page of results.
            // nextPageAsync will return null if there are no more results.
            val page = result.nextPageAsync.get()

            // Map the search results to Todo documents.
            // This will return null if the result is empty.
            // If the result is not empty, it will return a list of Todo documents.
            page.mapNotNull {
                if (it.genericDocument.schemaType == Todo::class.java.simpleName) {
                    it.getDocument(Todo::class.java)
                } else null
            }
        }
    }

    // Close the session.
    // Set the session to null.
    fun closeSession() {
        session?.close()
        session = null
    }

    companion object {
        private const val DATABASE_NAME = "todo"
    }
}