package com.compose.experiment

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import com.compose.experiment.presentations.fingerprint.biometrics2.BiometricAppCompatActivity
import com.compose.experiment.ui.theme.ExperimentLabTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {


    @OptIn(ExperimentalComposeUiApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            ExperimentLabTheme(dynamicColor = false) {
                startActivity(Intent(this, BiometricAppCompatActivity::class.java))
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
