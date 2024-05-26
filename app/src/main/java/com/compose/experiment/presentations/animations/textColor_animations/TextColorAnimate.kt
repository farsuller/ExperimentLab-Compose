package com.compose.experiment.presentations.animations.textColor_animations

import androidx.compose.animation.animateColor
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@Composable
fun TextColorAnimate() {

    // `rememberInfiniteTransition` creates an infinite transition that allows animations to run indefinitely.
    val transition = rememberInfiniteTransition(label = "animate")

    // `animateColor` creates an animation for a color value.
    // The animation infinitely repeats between the `initialValue` (Color.Red) and `targetValue` (Color.Blue) with a reverse mode,
    // meaning it changes from Red to Blue and then back to Red.
    val animatedColor by transition.animateColor(
        initialValue = Color.Red,
        targetValue = Color.Blue,
        animationSpec = infiniteRepeatable(
            animation = tween(1000), // Duration of the animation.
            repeatMode = RepeatMode.Reverse // Reverses direction after reaching the target value.
        ),
        label = "animate"
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(4.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        // Apply the animated color to the text.
        Text(
            text = "Animate Color Text",
            fontSize = 30.sp,
            color = animatedColor
        )
    }
}