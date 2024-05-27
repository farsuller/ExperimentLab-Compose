package com.compose.experiment

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.fragment.app.FragmentActivity
import com.compose.experiment.presentations.fingerprint.BiometricAuthenticator
import com.compose.experiment.presentations.fingerprint.FingerprintFragmentActivity
import com.compose.experiment.ui.theme.ExperimentLabTheme
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : ComponentActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            ExperimentLabTheme(dynamicColor = false) {

                startActivity(Intent(this, FingerprintFragmentActivity::class.java))
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
