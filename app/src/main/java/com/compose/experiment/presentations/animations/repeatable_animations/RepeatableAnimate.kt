package com.compose.experiment.presentations.animations.repeatable_animations

import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
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
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp


@Composable
fun RepeatableAnimate() {

    // `rememberInfiniteTransition` creates an infinite transition that allows animations to run indefinitely.
    val transition = rememberInfiniteTransition(label = "animate")

    // `animateFloat` creates an animation for a float value, which in this case, scales the text.
    // The animation infinitely repeats between the `initialValue` (1F) and `targetValue` (3F) with a reverse mode,
    // meaning it scales up to 3F and then scales back down to 1F.
    val scale by transition.animateFloat(
        initialValue = 1F,
        targetValue = 3F,
        animationSpec = infiniteRepeatable(
            animation = tween(500), // Duration of the animation.
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

        Text(
            modifier = Modifier.graphicsLayer {
                scaleX = scale // Apply the animated scale to the X axis.
                scaleY = scale // Apply the animated scale to the Y axis.
                transformOrigin = TransformOrigin.Center // Set the origin of the transformation to the center.
            },
            text = "Animate Repeatable",
        )
    }
}