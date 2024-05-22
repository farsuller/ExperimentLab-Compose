package com.compose.experiment

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import com.compose.experiment.presentations.animations.alpha_animations.AnimateAlpha
import com.compose.experiment.presentations.animations.repeatable_animations.RepeatableAnimate
import com.compose.experiment.presentations.animations.textColor_animations.TextColorAnimate
import com.compose.experiment.ui.theme.ExperimentLabTheme
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : ComponentActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            ExperimentLabTheme(dynamicColor = false) {
                TextColorAnimate()
            }
        }
    }
}


@SuppressLint("UnnecessaryComposedModifier")
fun Modifier.clickableWithoutRipple(
    interactionSource: MutableInteractionSource,
    onClick: () -> Unit
) = composed(
    factory = {
        this.then(
            Modifier.clickable(
                interactionSource = interactionSource,
                indication = null,
                onClick = { onClick() }
            )
        )
    }
)
