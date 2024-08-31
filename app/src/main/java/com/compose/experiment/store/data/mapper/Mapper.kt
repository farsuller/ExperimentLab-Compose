package com.compose.experiment.store.data.mapper

import com.compose.experiment.store.domain.model.ApiError
import com.compose.experiment.store.domain.model.NetworkError
import retrofit2.HttpException
import java.io.IOException

fun Throwable.toNetworkError(): NetworkError{
    val error = when(this){
        is IOException -> ApiError.NetworkError
        is HttpException -> ApiError.UnknownResponse
        else -> ApiError.UnknownError
    }

    return NetworkError( error = error, t = this)

}