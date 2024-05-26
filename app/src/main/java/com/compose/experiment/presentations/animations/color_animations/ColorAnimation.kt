package com.compose.experiment.presentations.animations.color_animations

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun ColorAnimation() {
    val defaultText = "this is color animations"

    val defaultColor = Color(0xff212121)
    val changeColor = Color(0xFFFF5050)
    var boxColor by remember { mutableStateOf(defaultColor) }

    // `animationColor` is a state variable that smoothly animates between the current and target values of `boxColor`.
    // `animateColorAsState` handles creating the color transition animation with the specified `tween` duration of 500ms.
    val animationColor by animateColorAsState(
        targetValue = boxColor,
        animationSpec = tween(500),
        label = "animate color"
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(4.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(4.dp))
                .background(animationColor) // Apply the animated color to the background of the Box.
                .padding(4.dp)
                .animateContentSize(tween(500))
        ) {
            Text(
                text = defaultText,
                color = Color.White
            )
        }

        Spacer(modifier = Modifier.width(8.dp))

        Button(
            onClick = {
                // Toggle the box color between `defaultColor` and `changeColor`.
                boxColor = if (boxColor == defaultColor) changeColor else defaultColor
            },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(4.dp)
        ) {
            Text(text = "Change Color")
        }
    }
}