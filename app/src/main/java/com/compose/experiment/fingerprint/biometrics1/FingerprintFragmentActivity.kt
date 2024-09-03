package com.compose.experiment.fingerprint.biometrics1

import android.os.Bundle
import androidx.activity.compose.setContent
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.fragment.app.FragmentActivity
import com.compose.experiment.ui.theme.ExperimentLabTheme



class FingerprintFragmentActivity : FragmentActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Initialize the biometric authenticator
        val biometricAuthenticator = BiometricAuthenticator(this)
        setContent {
            ExperimentLabTheme(dynamicColor = false) {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    // Get the current activity context
                    val activity = LocalContext.current as FragmentActivity

                    // Create a state variable to hold the authentication message
                    var message by remember {
                        mutableStateOf("")
                    }

                    // Create a button to trigger biometric authentication
                    TextButton(onClick = {
                        biometricAuthenticator.promptBiometricAuth(
                            title = "Login",
                            subtitle = "Use your fingerprint or face id",
                            negativeButtonText = "Cancel",
                            fragmentActivity = activity,
                            onSuccess = {
                                // Update the message on successful authentication
                                message = "Success"
                            },
                            onError = { id, error ->
                                // Update the message on authentication error
                                message = error
                            },
                            onFailed = {
                                // Update the message on authentication failure
                                message = "Wrong fingerprint or face id"
                            }
                        )
                    }) {

                        Text(text = "Login with fingerprint or face id")
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(text = message)
                }
            }
        }
    }
}
