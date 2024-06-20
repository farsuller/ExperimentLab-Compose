package com.compose.experiment

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class ExperimentApplication : Application(){

    override fun onCreate() {
        super.onCreate()
        // Check if the device is running Android O (Oreo) or higher
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // Create a NotificationChannel for the foreground service
            val channel = NotificationChannel(
                "running_channel", // Unique ID for the channel
                "Running Notifications", // User-visible name of the channel
                NotificationManager.IMPORTANCE_HIGH // Importance level for the notifications
            )

            // Get the NotificationManager system service to create the channel
            val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            // Create the notification channel
            notificationManager.createNotificationChannel(channel)
        }
    }

}