package com.compose.experiment.presentations.animations.offset_animations

import androidx.compose.animation.core.animateOffsetAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
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
    val targetX = 50F
    val targetY = 50F

    var isDefault by remember { mutableStateOf(true) }

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
                    .offset(offset.x.dp, offset.y.dp)
                    .background(Color.Red)
            )
        }

        Button(
            onClick = {
                isDefault = !isDefault
            },
            shape = RoundedCornerShape(4.dp),
        ) {
            Text(text = "Animate Offset")
        }
    }
}