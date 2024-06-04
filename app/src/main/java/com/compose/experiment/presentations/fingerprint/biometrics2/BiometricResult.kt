package com.compose.experiment.presentations.fingerprint.biometrics2

sealed interface BiometricResult {
    data object HardwareUnavailable : BiometricResult
    data object FeatureUnavailable : BiometricResult
    data object AuthenticationFailed : BiometricResult
    data object AuthenticationSuccess : BiometricResult
    data object AuthenticationNotEnrolled : BiometricResult
    data class AuthenticationError(val error: String) : BiometricResult
}