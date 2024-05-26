package com.compose.experiment.presentations.animations.content_animations

import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.SizeTransform
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
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


@SuppressLint("UnusedContentLambdaTargetStateParameter")

@Composable
fun ContentAnimation() {

    var age by remember {
        mutableStateOf(18)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(4.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        // `AnimatedContent` is used to animate changes in the content when `age` changes.
        // The `transitionSpec` defines how the animation should occur based on the change in `age`.
        AnimatedContent(targetState = age, label = "animate content", transitionSpec = {
            if (targetState > initialState) {
                // Animation for when the target state is greater than the initial state (increment age)
                (slideInVertically { height -> height } + fadeIn())
                    .togetherWith(slideOutVertically { height -> -height } + fadeOut())
            } else {
                // Animation for when the target state is less than the initial state (decrement age)
                (slideInVertically { height -> -height } + fadeIn())
                    .togetherWith(slideOutVertically { height -> height } + fadeOut())
            }.using(SizeTransform(clip = false))
        }) {
            Text(text = age.toString())
        }

        Spacer(modifier = Modifier.height(8.dp))

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Button(
                onClick = { age++ },
                shape = RoundedCornerShape(4.dp),
            ) {
                Text(text = "Age+")
            }
            Spacer(modifier = Modifier.width(8.dp))
            Button(
                onClick = { age-- },
                shape = RoundedCornerShape(4.dp),
            ) {
                Text(text = "Age-")
            }
        }
    }
}