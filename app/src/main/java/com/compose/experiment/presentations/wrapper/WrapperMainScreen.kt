package com.compose.experiment.presentations.wrapper

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.compose.experiment.MainViewModel

@Composable
fun WrapperMainScreen() {

    val viewmodel: MainViewModel = viewModel()

//    val isLoading by viewmodel.isLoading
//    val error by viewmodel.error
//    val data = viewmodel.data
//
//
//    if (isLoading) {
//        WrapperMainContent()
//    } else if (error != null) {
//        WrapperMainContent(text = error.toString())
//    } else {
//        if (data.isNotEmpty()) {
//            WrapperMainContent(text = data.joinToString())
//        } else {
//            WrapperMainContent("Empty")
//        }
//    }

    val data by viewmodel.data.collectAsState(initial = RequestState.Idle)

    data.DisplayResult(
        onLoading = { WrapperMainContent() },
        onSuccess = { WrapperMainContent(data.getSuccessData().joinToString()) },
        onError = { WrapperMainContent(data.getErrorMessage()) })

//    if(data.isLoading()){
//        WrapperMainContent()
//    } else if(data.isSuccess()){
//        WrapperMainContent(data.getSuccessData().joinToString())
//    } else if(data.isError()){
//        WrapperMainContent(data.getErrorMessage())
//    }
}


@Composable
fun WrapperMainContent(text: String? = null) {

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        if (text != null) {

            Text(
                text = text,
                fontSize = MaterialTheme.typography.titleLarge.fontSize
            )
        } else {
            CircularProgressIndicator()
        }
    }
}