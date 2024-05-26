package com.compose.experiment.presentations.animations.alpha_animations

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp


@Composable
fun AnimateAlpha() {

    // `alpha` is the state variable that controls the transparency level of the Box.
    // It is initially set to 1F, which means fully opaque.
    var alpha by remember {
        mutableFloatStateOf(1F)
    }

    // `animateAlpha` is a state variable that smoothly animates between the current and target values of `alpha`.
    // `animateFloatAsState` takes care of creating the animation with the given `tween` specification of 500ms.
    val animateAlpha by animateFloatAsState(targetValue = alpha, animationSpec = tween(500), label = "animate alpha")

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(4.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Box(
            modifier = Modifier
                .size(60.dp)
                .alpha(alpha = animateAlpha) // Apply the animated alpha value to the Box.
                .background(Color.Red)
        )

        Button(
            onClick = {
                // Toggle the alpha value between 0F (fully transparent) and 1F (fully opaque).
                alpha = if (alpha == 0F) 1F else 0F
            },
            shape = RoundedCornerShape(4.dp),
        ) {
            Text(text = "Animate Alpha")
        }
    }
}