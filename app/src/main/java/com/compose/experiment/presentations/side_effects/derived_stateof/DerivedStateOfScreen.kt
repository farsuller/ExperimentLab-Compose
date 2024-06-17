package com.compose.experiment.presentations.side_effects.derived_stateof

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch

@Composable
fun DerivedStateOfScreen() {

    val numbers = remember {
        (0..50).toList()
    }
    val scrollState = rememberLazyListState()

    //derivedStateOf is a composable function that takes a lambda and returns a State object
    //other composables can observe the State object and will be recomposed when the value changes
    //derivedStateOf is useful when you need to compute a value based on other values
    //in this case, we are checking if the first visible item index is greater than or equal to 5
    //if it is, we are making the floating action button visible,if it is not, we are making the floating action button invisible
    //this is use as well for computation of values based on other values
    val isFabVisible by remember {
        derivedStateOf {
            scrollState.firstVisibleItemIndex >= 5
        }
    }

    //Since LaunchEffect is a composable function, we need to use rememberCoroutineScope
    //rememberCoroutineScope is a composable function that returns a CoroutineScope
    //that can be use to launch coroutines and inside callbacks of those coroutines
    val scope = rememberCoroutineScope()

    Scaffold(
        floatingActionButton = {
            if (isFabVisible) {
                FloatingActionButton(onClick = {
                    scope.launch {
                        scrollState.animateScrollToItem(0)
                    }
                }) {
                    Icon(imageVector = Icons.Default.KeyboardArrowUp, contentDescription = null)
                }
            }

        }
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = it.calculateTopPadding(), bottom = it.calculateBottomPadding()),
            state = scrollState
        ) {
            items(numbers) {
                Text(
                    text = it.toString(),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                )
            }

        }
    }

}