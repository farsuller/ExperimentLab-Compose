package com.compose.experiment.fingerprint.biometrics2

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricManager.Authenticators.BIOMETRIC_STRONG
import androidx.biometric.BiometricManager.Authenticators.DEVICE_CREDENTIAL
import androidx.biometric.BiometricPrompt
import androidx.biometric.BiometricPrompt.PromptInfo
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow

// This class manages the biometric prompt and handles the authentication results.
class BiometricPromptManager(private val activity: AppCompatActivity) {

    // Channel to send biometric authentication results.
    private val resultChannel = Channel<BiometricResult>()

    // Flow to collect the results of the biometric prompt.
    val promptResults = resultChannel.receiveAsFlow()

    // Function to display the biometric prompt with given title and description.
    fun showBiometricPrompt(
        title: String,
        description: String
    ) {
        // Get the BiometricManager instance.
        val manager = BiometricManager.from(activity)

        // Define the authenticators based on the Android version.
        val authenticators = if (Build.VERSION.SDK_INT >= 30) { BIOMETRIC_STRONG or DEVICE_CREDENTIAL } else { BIOMETRIC_STRONG }

        // Build the biometric prompt information.
        val promptInfo = PromptInfo.Builder()
            .setTitle(title)
            .setDescription(description)
            .setAllowedAuthenticators(authenticators)

        // For older Android versions, set a negative button text.
        if(Build.VERSION.SDK_INT < 30){
            promptInfo.setNegativeButtonText("Cancel")
        }

        // Check the availability of biometric hardware and enrollment status.
        when(manager.canAuthenticate(authenticators)){
            BiometricManager.BIOMETRIC_ERROR_HW_UNAVAILABLE -> {
                resultChannel.trySend(BiometricResult.HardwareUnavailable)
                return
            }
            BiometricManager.BIOMETRIC_ERROR_NO_HARDWARE -> {
                resultChannel.trySend(BiometricResult.FeatureUnavailable)
                return
            }
            BiometricManager.BIOMETRIC_ERROR_NONE_ENROLLED -> {
                resultChannel.trySend(BiometricResult.AuthenticationNotEnrolled)
                return
            }
            else -> Unit
        }

        // Create a BiometricPrompt instance with the authentication callbacks.
        val prompt = BiometricPrompt( activity,
            object : BiometricPrompt.AuthenticationCallback(){
                override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
                    super.onAuthenticationError(errorCode, errString)
                    resultChannel.trySend(BiometricResult.AuthenticationError(errString.toString()))
                }

                override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                    super.onAuthenticationSucceeded(result)
                    resultChannel.trySend(BiometricResult.AuthenticationSuccess)
                }

                override fun onAuthenticationFailed() {
                    super.onAuthenticationFailed()
                    resultChannel.trySend(BiometricResult.AuthenticationFailed)
                }
            }
        )
        // Authenticate the user with the prompt information.
        prompt.authenticate(promptInfo.build())
    }
}