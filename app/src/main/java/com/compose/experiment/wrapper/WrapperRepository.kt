package com.compose.experiment.wrapper

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class WrapperRepository  @Inject constructor(){
    val myData = listOf("Kotlin", "Compose", "Multiplatform")

    fun fetchData() : Flow<List<String>> {
        return flow {
            throw IllegalArgumentException("Something went wrong.")
          //  emit(myData)
        }
    }

    fun fetchDataWithWrapper() : Flow<RequestState<List<String>>>{
        return flow {
            emit(RequestState.Loading)
            delay(2000)
            //emit(RequestState.Error("Something went wrong."))
            emit(RequestState.Success(data = myData))
        }
    }
}
