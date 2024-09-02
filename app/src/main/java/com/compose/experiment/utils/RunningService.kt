package com.compose.experiment.utils

import android.app.Service
import android.content.Intent
import android.os.IBinder
import androidx.core.app.NotificationCompat
import com.compose.experiment.R

class RunningService : Service() {

    // This method is required to be overridden, but this service does not support binding, so return null
    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    // This method is called when the service is started with startService() or startForegroundService()
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        // Check the action in the intent and perform the corresponding operation
        when (intent?.action) {
            // If the action is START, call the start method to start the foreground service
            Actions.START.toString() -> start()

            // If the action is STOP, stop the service
            Actions.STOP.toString() -> stopSelf()
        }
        // Call the super method to handle the command
        return super.onStartCommand(intent, flags, startId)
    }

    // Method to start the foreground service with a notification
    private fun start(){
        // Create a notification with the NotificationCompat.Builder
        val notification = NotificationCompat.Builder(this, "running_channel")
            .setSmallIcon(R.drawable.ic_launcher_foreground) // Set the small icon for the notification
            .setContentTitle("Run is active") // Set the title of the notification
            .setContentText("Running") // Set the content text of the notification
            .build()
        // Start the service in the foreground with the notification
        startForeground(1, notification)
    }

    // Enum class to define the actions that can be performed on the service
    enum class Actions {
        START, // Action to start the service
        STOP // Action to stop the service
    }
}