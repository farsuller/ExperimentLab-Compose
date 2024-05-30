package com.compose.experiment.wrapper

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.runtime.Composable

// A sealed class representing different states of a request
sealed class RequestState<out T> {

    // State representing an idle state (no request made)
    data object Idle : RequestState<Nothing>()

    // State representing a loading state (request in progress)
    data object Loading : RequestState<Nothing>()

    // State representing a successful request with data
    data class Success<T>(val data: T) : RequestState<T>()

    // State representing a failed request with an error message
    data class Error(val message: String) : RequestState<Nothing>()

    // Helper function to check if the current state is Loading
    fun isLoading() = this is Loading

    // Helper function to check if the current state is Success
    fun isSuccess() = this is Success

    // Helper function to check if the current state is Error
    fun isError() = this is Error

    /**
     * Returns data from a [Success].
     * @throws ClassCastException If the current state is not [Success]
     *  */
    fun getSuccessData() = (this as Success).data

    /**
     * Returns data from a [Success] state or null if the state is not [Success].
     */
    fun getSuccessDataOrNull(): T? {
        return try {
            (this as Success).data
        } catch (e: Exception) {
            null
        }
    }

    /**
     * Returns an error message from an [Error]
     * @throws ClassCastException If the current state is not [Error]
     *  */
    fun getErrorMessage() = (this as Error).message

    /**
     * Returns an error message from an [Error] state or null if the state is not [Error].
     */
    fun getErrorMessageOrNull(): String? {
        return try {
            (this as Error).message
        } catch (e: Exception) {
            null
        }
    }

    /**
     * Composable function to display different UI content based on the request state.
     * @param onIdle Composable function to display when the state is [Idle]
     * @param onLoading Composable function to display when the state is [Loading]
     * @param onSuccess Composable function to display with data when the state is [Success]
     * @param onError Composable function to display with an error message when the state is [Error]
     */
    @Composable
    fun DisplayResult(
        onIdle: (@Composable () -> Unit)? = null,
        onLoading: @Composable () -> Unit,
        onSuccess: @Composable (T) -> Unit,
        onError: @Composable (String) -> Unit,
    ) {
        // Animated content transition based on the current state
        AnimatedContent(
            targetState = this,
            transitionSpec = {
                fadeIn(tween(durationMillis = 300)) togetherWith
                        fadeOut(tween(durationMillis = 300))
            },
            label = "Content Animation"
        ) { state ->
            // Display appropriate content based on the current state
            when (state) {
                is Idle -> {
                    onIdle?.invoke()
                }

                is Loading -> {
                    onLoading()
                }

                is Success -> {
                    onSuccess(state.getSuccessData())
                }

                is Error -> {
                    onError(state.getErrorMessage())
                }
            }
        }
    }
}