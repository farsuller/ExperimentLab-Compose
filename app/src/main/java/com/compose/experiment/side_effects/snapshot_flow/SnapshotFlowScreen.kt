package com.compose.experiment.side_effects.snapshot_flow

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import kotlinx.coroutines.flow.filter

@Composable
fun SnapshotFlowScreen() {

    var text by remember { mutableStateOf("") }

    //snapshot is a function that takes a lambda and returns a StateFlow
    //this is used to observe changes to the state of a composable
    //in this case, we are observing changes to the text state
    //and if the length of the text is greater than 5, we will collect it
    // you can check the documentation for more information on snapshotFlow https://developer.android.com/jetpack/compose/side-effects#snapshotFlow
    val textFlow = snapshotFlow { text }

    LaunchedEffect(key1 = Unit) {
        textFlow.filter {
            it.length > 5
        }.collect {
            println("Collected text: $it")
        }
    }

    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        TextField(
            value = text,
            onValueChange = {
                text = it
            }
        )
    }

}