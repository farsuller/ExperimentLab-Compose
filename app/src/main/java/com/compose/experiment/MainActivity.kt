package com.compose.experiment

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import com.compose.experiment.ui.theme.ExperimentLabTheme
import com.compose.experiment.utils.sharedPreferences
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    // Launcher to handle the result of the permission request
    private val requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permissions ->
            // Check if all permissions were granted
            val allGranted = permissions.entries.all { it.value }
            if (!allGranted) {
                // Handle the case where not all permissions are granted
                // (you may want to show a message to the user or disable some features)
            }
        }


    private var token by sharedPreferences(name = "token")

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // Request necessary location and notification permissions
        // requestLocationPermissions()
        token = ""
        setContent {
            ExperimentLabTheme(dynamicColor = false) {


            }
        }
    }



}
