package com.compose.experiment.side_effects.side_effect

import android.app.Activity
import android.graphics.Color
import android.os.Build
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.platform.LocalContext

@Composable
fun SystemBarColorChangerScreen(
    statusBarColor: Color?,
    navigationBarColor: Color?
){
    //window has nothing to do with jetpack compose ui
    val window = (LocalContext.current as Activity).window

    //side effect is used on non compose state inside a composable function
    SideEffect {
        statusBarColor?.let {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                window.statusBarColor = it.toArgb()
            }
        }

        navigationBarColor?.let {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                window.navigationBarColor = it.toArgb()
            }
        }
    }

}