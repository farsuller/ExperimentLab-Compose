package com.compose.experiment.presentations.fingerprint.biometrics2

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.biometric.BiometricManager.Authenticators.BIOMETRIC_STRONG
import androidx.biometric.BiometricManager.Authenticators.DEVICE_CREDENTIAL
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.compose.experiment.presentations.fingerprint.biometrics2.BiometricPromptManager.BiometricResult
import com.compose.experiment.ui.theme.ExperimentLabTheme


class BiometricAppCompatActivity : AppCompatActivity() {

    private val promptManager by lazy {
        BiometricPromptManager(this)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        setContent {
            ExperimentLabTheme(dynamicColor = false) {

                val biometricResult by promptManager.promptResults.collectAsState(initial = null)

                val enrollLauncher = rememberLauncherForActivityResult(contract = ActivityResultContracts.StartActivityForResult(),
                    onResult = {
                        println("Activity Result $it")
                    })
                LaunchedEffect(biometricResult) {
                    if(biometricResult is BiometricResult.AuthenticationNotEnrolled){
                        if (Build.VERSION.SDK_INT >= 30){
                            val enrollIntent = Intent(Settings.ACTION_BIOMETRIC_ENROLL).apply {
                                putExtra(
                                    Settings.EXTRA_BIOMETRIC_AUTHENTICATORS_ALLOWED,
                                    BIOMETRIC_STRONG or DEVICE_CREDENTIAL
                                )
                            }
                            enrollLauncher.launch(enrollIntent)
                        }
                    }

                }
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Button(onClick = {
                        promptManager.showBiometricPrompt(
                            title = "Sample Prompt",
                            description = "Sample Description"
                        )
                    }) {
                        Text(text = "Authenticate")
                    }

                    biometricResult?.let {result->
                        
                        Text(text = when(result){
                            is BiometricResult.AuthenticationError -> "Authentication Error: ${result.error}"
                            BiometricResult.AuthenticationFailed -> "Authentication Failed"
                            BiometricResult.AuthenticationNotEnrolled -> "Authentication Not Enrolled"
                            BiometricResult.AuthenticationSuccess -> "Authentication Success"
                            BiometricResult.FeatureUnavailable -> "Feature Unavailable"
                            BiometricResult.HardwareUnavailable -> "Hardware Unavailable"
                        })

                    }

                }
            }
        }
    }
}
