package com.compose.experiment.side_effects.remember_coroutinescope

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch

@Composable
fun RememberCoroutineScopeScreen() {

    val names = remember {
        (0..100).map { "Name $it" }
    }
    val scrollState = rememberScrollState()


    //Since LaunchEffect is a composable function, we need to use rememberCoroutineScope
    //rememberCoroutineScope is a composable function that returns a CoroutineScope
    //that can be use to launch coroutines and inside callbacks of those coroutines
    val scope = rememberCoroutineScope()
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = {
                scope.launch {
                    scrollState.animateScrollTo(scrollState.maxValue)
                }
            }) {
                Icon(imageVector = Icons.Default.KeyboardArrowDown, contentDescription = null)
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    top = paddingValues.calculateTopPadding(),
                    bottom = paddingValues.calculateBottomPadding()
                )
                .verticalScroll(state = scrollState)
        ) {
            names.forEach { name ->
                Text(text = name, modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp))
            }

        }
    }

}


