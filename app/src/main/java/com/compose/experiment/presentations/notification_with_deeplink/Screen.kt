package com.compose.experiment.presentations.notification_with_deeplink

import kotlinx.serialization.Serializable

@Serializable
data object NotificationMainScreen

@Serializable
data class NotificationDetailsScreen(val message: String)
