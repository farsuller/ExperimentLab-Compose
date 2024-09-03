package com.compose.experiment.foreground_ui

import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.compose.experiment.utils.RunningService

@Composable
fun ForegroundServiceScreen(applicationContext : Context) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Button(
            onClick = {
                Intent(applicationContext, RunningService::class.java).also {
                    it.action = RunningService.Actions.START.toString()
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        applicationContext.startForegroundService(it)
                    } else {
                        applicationContext.startService(it)
                    }

                }
            }
        ) {
            Text(text = "Start run service")
        }

        Button(
            onClick = {
                Intent(applicationContext, RunningService::class.java).also {
                    it.action = RunningService.Actions.STOP.toString()
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        applicationContext.startForegroundService(it)
                    } else {
                        applicationContext.startService(it)
                    }
                }
            }
        ) {
            Text(text = "Stop run service")
        }
    }
}