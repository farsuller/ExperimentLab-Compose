package com.compose.experiment

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.datastore.dataStore
import androidx.lifecycle.lifecycleScope
import androidx.wear.compose.material.Text
import com.compose.experiment.encryption.UserPreferences
import com.compose.experiment.encryption.UserPreferencesSerializer
import com.compose.experiment.kotlinxcustomserializer.BookWorkDto
import com.compose.experiment.ui.theme.ExperimentLabTheme
import com.compose.experiment.utils.sharedPreferences
import dagger.hilt.android.AndroidEntryPoint
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json

val Context.dataStore by dataStore(
    fileName = "user-preferences",
    serializer = UserPreferencesSerializer
)

val SECRET_TOKEN = "Hello, you decrypted me great!"


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
        setContent {
            ExperimentLabTheme(dynamicColor = false) {
                Surface(
                    modifier = Modifier.background(MaterialTheme.colorScheme.primary)
                ) {
                    val scope = rememberCoroutineScope()
                    var text by remember { mutableStateOf("") }

                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Button(onClick = {
                                scope.launch {
                                    dataStore.updateData {
                                        UserPreferences(token = SECRET_TOKEN)
                                    }
                                }
                            }) {
                                Text(text = "Encrypt")
                            }

                            Button(onClick = {
                                scope.launch {
                                    text = dataStore.data.first()?.token ?: ""
                                }
                            }) {
                                Text(text = "Decrypt")
                            }


                            Text(
                                color = Color.Black,
                                text = text
                            )
                        }
                    }

                }
            }
        }
    }

    private fun ktorCustomDeserialize() {
        val httpClient = HttpClient(OkHttp) {
            install(ContentNegotiation) {
                json(
                    json = Json {
                        ignoreUnknownKeys = true
                    }
                )
            }
        }
        val jsonObjectEndpoint = "https://openlibrary.org/works/OL82563W.json"
        val stringEndpoint = "https://openlibrary.org/works/OL1968368W.json"

        lifecycleScope.launch {
            listOf(
                stringEndpoint,
                jsonObjectEndpoint

            ).forEach { url ->
                val response = httpClient.get(
                    urlString = url
                )

                val dto = response.body<BookWorkDto>()

                println("Book description is ${dto.description}")
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
