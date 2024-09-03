package com.compose.experiment.side_effects.launched_effect

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import kotlinx.coroutines.delay
import kotlin.time.Duration.Companion.seconds

@Composable
fun LaunchedEffectScreen() {
    val listCount = remember { mutableStateListOf<Int>() }

    Scaffold { paddingValues ->
        Counter(max = 5,
            onCountChange = {
                listCount.add(it)
                println("Count: ${listCount.toList()}")
            })

        LazyColumn(
            modifier = Modifier.padding(
                top = paddingValues.calculateTopPadding(),
                bottom = paddingValues.calculateBottomPadding()
            )
        ) {
            items(listCount) { count ->
                Text(text = "Count: $count")
            }
        }
    }

}


@Composable
fun Counter(max: Int, onCountChange: (Int) -> Unit) {
    // Declare a mutable state variable 'counter' that will be remembered across recompositions
    var counter by remember { mutableIntStateOf(0) }

    // LaunchedEffect is a composable function that runs a suspending block of code when its key(s) change
    // It takes one or more keys as parameters. The coroutine block inside LaunchedEffect will run whenever any of the keys change.
    // Here, counter is used as the key, so the effect will run initially and whenever counter changes.
    LaunchedEffect(key1 = counter) {
        if (counter >= max + 1) {
            return@LaunchedEffect
        }
        delay(1.seconds)
        onCountChange(counter)
        counter++
    }
}