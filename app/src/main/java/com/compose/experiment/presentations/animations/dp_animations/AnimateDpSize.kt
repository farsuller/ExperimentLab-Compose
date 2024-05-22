package com.compose.experiment.presentations.animations.dp_animations

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
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
fun AnimateDpSize() {

    val defaultDp = 100.dp
    val changeDp = 200.dp

    var buttonWidth by remember {
        mutableStateOf(defaultDp)
    }

    val animateSize by animateDpAsState(
        targetValue = buttonWidth,
        animationSpec = tween(500),
        label = "animateDp"
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(4.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Button(
            onClick = {
                buttonWidth = if (buttonWidth == defaultDp) changeDp else defaultDp
            },
            modifier = Modifier.width(animateSize),
            shape = RoundedCornerShape(4.dp),
        ) {
            Text(text = "Change Size")
        }
    }
}