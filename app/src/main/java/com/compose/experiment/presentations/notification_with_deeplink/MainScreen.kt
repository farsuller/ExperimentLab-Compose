package com.compose.experiment.presentations.notification_with_deeplink

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.compose.experiment.MainViewModel

@Composable
fun MainScreen(navController: NavHostController) {
    val viewModel: MainViewModel = hiltViewModel()
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(onClick = viewModel::showSimpleNotification) {
            Text(text = "Simple Notification")
        }
        Spacer(modifier = Modifier.height(12.dp))
        Button(onClick = viewModel::updateSimpleNotification) {
            Text(text = "Update Notification")
        }
        Spacer(modifier = Modifier.height(12.dp))
        Button(onClick = viewModel::cancelSimpleNotification) {
            Text(text = "Cancel Notification")
        }
        Spacer(modifier = Modifier.height(12.dp))
        Button(onClick = {
            navController.navigate(NotificationDetailsScreen(message = "Coming from Main Screen."))
        }) {
            Text(text = "DETAILS SCREEN")
        }
    }
}