package com.compose.experiment.presentations.side_effects.remember_updated_state

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import kotlin.time.Duration.Companion.seconds


@Composable
fun RememberUpdatedStateScreen() {

    Scaffold {
        TimeoutWithTwoButtons(paddingValues = it)
    }

}

@Composable
fun TimeoutWithTwoButtons(paddingValues: PaddingValues = PaddingValues()) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(
                top = paddingValues.calculateTopPadding(),
                bottom = paddingValues.calculateBottomPadding()
            )
    ) {
        var clickedButtonColor by remember {
            mutableStateOf("Unknown")
        }

        Button(
            onClick = { clickedButtonColor = "Red" },
            colors = ButtonDefaults.buttonColors(containerColor = Color.Red)
        ) {
            Text(text = "Red Button")
        }
        Spacer(modifier = Modifier.height(10.dp))
        Button(
            onClick = { clickedButtonColor = "Black" },
            colors = ButtonDefaults.buttonColors(containerColor = Color.Black)
        ) {
            Text(text = "Black Button")
        }

        Timeout(buttonColor = clickedButtonColor)
    }
}

@Composable
fun Timeout(buttonColor: String) {
    Log.d("Timeout", "Composing timer with color: $buttonColor")

    //rememberUpdateState will return the latest value of buttonColor or the value it was updated to
    // after the recomposition of the screen or the timeout has ended.
    // other sample usage of rememberUpdatedState: https://developer.android.com/jetpack/compose/side-effects#rememberupdatedstate
    val buttonColorUpdated = rememberUpdatedState(newValue = buttonColor)

    LaunchedEffect(key1 = null) {
        delay(10.seconds)
        Log.d("Timeout", "Timeout ended")
        Log.d("Timeout", "Last pressed button color: ${buttonColorUpdated.value}")

    }

}