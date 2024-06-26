package com.compose.experiment.presentations.animations.offset_animations

import androidx.compose.animation.core.animateOffsetAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp



@Composable
fun AnimateOffset() {

    val defaultX = 0f
    val defaultY = 0f
    val targetX = 50f
    val targetY = 50f

    var isDefault by remember { mutableStateOf(true) }

    // `offset` is a state variable that smoothly animates between the current and target offset values.
    // `animateOffsetAsState` handles creating the offset transition animation with the specified `tween` duration of 500ms.
    val offset by animateOffsetAsState(
        targetValue = if (isDefault) Offset(defaultX, defaultY) else Offset(targetX, targetY),
        animationSpec = tween(500),
        label = "animate label"
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(4.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Box(modifier = Modifier.size(300.dp)) {
            Box(
                modifier = Modifier
                    .size(60.dp)
                    .offset(offset.x.dp, offset.y.dp) // Apply the animated offset to the inner Box.
                    .background(Color.Red)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                // Toggle the offset between the default position and the target position.
                isDefault = !isDefault
            },
            shape = RoundedCornerShape(4.dp),
        ) {
            Text(text = "Animate Offset")
        }
    }
}