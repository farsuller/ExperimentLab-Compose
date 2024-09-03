package com.compose.experiment.presentations.hotflow_coldflow

import android.Manifest
import androidx.activity.ComponentActivity
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.core.app.ActivityCompat
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.shareIn

@Composable
fun LocationFlowDemo() {

    val context = LocalContext.current

    LaunchedEffect(key1 = true) {
        ActivityCompat.requestPermissions(
            context as ComponentActivity,
            arrayOf(
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION
            ),
            0
        )
    }

    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        val scope = rememberCoroutineScope()

        val flow = remember {
            observeLocation(context)
                .shareIn(
                    scope,
                    SharingStarted.Eagerly,
                )
        }

        val location1 by flow.collectAsState(initial = null)
        val location2 by flow.collectAsState(initial = null)

        LaunchedEffect(location1) {
            println("Location1: update ${location1?.latitude}, ${location1?.longitude}")
        }
        LaunchedEffect(location1) {
            println("Location2: update ${location2?.latitude}, ${location2?.longitude}")
        }

        Column {
            Text(text = "Location1: ${location1?.latitude}, ${location1?.longitude}")
            Text(text = "Location2: ${location2?.latitude}, ${location2?.longitude}")
        }


    }
}