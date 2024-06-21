package com.compose.experiment

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import com.compose.experiment.presentations.pagination.PaginatedListScreen
import com.compose.experiment.ui.theme.ExperimentLabTheme
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

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // Request necessary location and notification permissions
        // requestLocationPermissions()

        setContent {
            ExperimentLabTheme(dynamicColor = false) {

                PaginatedListScreen()
            }
        }
    }

    // Function to request location and notification permissions
    fun requestLocationPermissions() {
        // List of required permissions based on Android version
        val requiredPermissions =
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.UPSIDE_DOWN_CAKE) {
                mutableListOf(
                    Manifest.permission.FOREGROUND_SERVICE_LOCATION,
                    Manifest.permission.ACCESS_FINE_LOCATION
                )
            } else {
                mutableListOf(
                    Manifest.permission.ACCESS_FINE_LOCATION
                )
            }

        // Add POST_NOTIFICATIONS permission for Android TIRAMISU and above
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            requiredPermissions.add(Manifest.permission.POST_NOTIFICATIONS)
        }

        // Add additional foreground service permissions for Android UPSIDE_DOWN_CAKE and above
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.UPSIDE_DOWN_CAKE) {
            requiredPermissions.add(Manifest.permission.FOREGROUND_SERVICE)
            requiredPermissions.add(Manifest.permission.FOREGROUND_SERVICE_DATA_SYNC)
        }

        // Filter out already granted permissions
        val permissionsToRequest = requiredPermissions.filter {
            ContextCompat.checkSelfPermission(this, it) != PackageManager.PERMISSION_GRANTED
        }

        // Request the remaining permissions
        if (permissionsToRequest.isNotEmpty()) {
            requestPermissionLauncher.launch(permissionsToRequest.toTypedArray())
        }
    }
}

