package com.compose.experiment.presentations.animations.visibility_animations

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp


@Composable
fun VisibilityAnimation() {

    var textVisible by remember {
        mutableStateOf(false)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(4.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        // `AnimatedVisibility` animates the visibility of the content based on the `textVisible` state.
        // When `textVisible` is true, the text will enter with a scale in and fade in animation.
        // When `textVisible` is false, the text will exit with a slide out vertically and fade out animation.
        AnimatedVisibility(
            visible = textVisible,
            enter = scaleIn() + fadeIn(), // Animation when becoming visible.
            exit = slideOutVertically() + fadeOut() // Animation when becoming invisible.
        ) {
            Text(text = "Visibility Text")
        }

        Spacer(modifier = Modifier.width(8.dp))

        Button(
            onClick = {
                // Toggle the visibility state of the text.
                textVisible = !textVisible
            },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(4.dp)
        ) {
            Text(text = if (textVisible) "Hide Text" else "Show Text")
        }
    }
}