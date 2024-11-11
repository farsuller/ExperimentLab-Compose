package com.compose.experiment

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Checkbox
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.compose.experiment.presentations.list_items.SAMPLE_FRUIT
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        token = ""
        setContent {
            ExperimentLabTheme(dynamicColor = false) {
                Surface {
                    Scaffold(
                        modifier = Modifier.fillMaxSize()
                    ){ innerPadding ->
                        var fruits by remember { mutableStateOf(SAMPLE_FRUIT) }
                        LazyColumn(
                            modifier = Modifier.fillMaxSize(),
                            contentPadding = innerPadding
                        ) {
                            items(
                                items = fruits,
                                key = { fruit -> fruit.name }){ fruit->
                                ListItem(
                                    headlineContent = {
                                        Text(text = fruit.name)
                                    },
                                    supportingContent = {
                                        Text(text = fruit.description)
                                    },
                                    overlineContent = {
                                        Text(text = fruit.category.name)
                                    },
                                    leadingContent = {
                                        Icon(
                                            imageVector = Icons.Default.ShoppingCart,
                                            contentDescription = null,
                                            tint = fruit.color
                                        )
                                    },
                                    trailingContent = {
                                        Checkbox(
                                            checked = fruit.isSelected,
                                            onCheckedChange = {
                                                fruits = fruits.map { currentFruit->
                                                    if (currentFruit == fruit){
                                                        currentFruit.copy(isSelected = !currentFruit.isSelected)
                                                    }else{
                                                        currentFruit
                                                    }
                                                }
                                            }
                                        )
                                    },
                                    modifier = Modifier.fillMaxWidth()
                                        .clickable {
                                            fruits = fruits.map { currentFruit->
                                                if (currentFruit == fruit){
                                                    currentFruit.copy(isSelected = !currentFruit.isSelected)
                                                }else{
                                                    currentFruit
                                                }
                                            }
                                        }
                                )
                                HorizontalDivider()

                            }
                        }
                    }

                }

            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray,
        deviceId: Int
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults, deviceId)
        if (requestCode == 101) {
            if (grantResults.all { it == PackageManager.PERMISSION_GRANTED }) {
                Toast.makeText(this, "Permission Granted", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show()
                finish()
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
    }
}
