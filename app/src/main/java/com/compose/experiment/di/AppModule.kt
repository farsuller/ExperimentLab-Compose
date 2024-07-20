package com.compose.experiment.di

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.TaskStackBuilder
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationCompat.VISIBILITY_PRIVATE
import androidx.core.app.NotificationManagerCompat
import androidx.core.net.toUri
import com.compose.experiment.MainActivity
import com.compose.experiment.R
import com.compose.experiment.presentations.local_search.TodoSearchManager
import com.compose.experiment.presentations.notification_with_deeplink.MY_ARG
import com.compose.experiment.presentations.notification_with_deeplink.MY_URI
import com.compose.experiment.receiver.MyReceiver
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    // Provides a singleton instance of NotificationCompat.Builder
    @Singleton
    @Provides
    fun provideNotificationBuilder(
        @ApplicationContext context: Context
    ): NotificationCompat.Builder {

        // Intent to be triggered when the notification action is clicked
        val intent = Intent(context, MyReceiver::class.java).apply {
            putExtra("MESSAGE", "Clicked!")  // Extra data sent with the intent
        }
        val flag = PendingIntent.FLAG_IMMUTABLE  // Flag to ensure the PendingIntent is immutable
        val pendingIntent = PendingIntent.getBroadcast(context, 0, intent, flag)  // PendingIntent for the broadcast

        // Intent to be triggered when the notification itself is clicked
        val clickIntent = Intent(
            Intent.ACTION_VIEW,
            "$MY_URI/$MY_ARG=Coming from Notification".toUri(),
            context,
            MainActivity::class.java
        )

        // TaskStackBuilder is used to create a back stack for the notification
        val clickPendingIntent: PendingIntent = TaskStackBuilder.create(context).run {
            addNextIntentWithParentStack(clickIntent)  // Ensures proper back stack behavior
            getPendingIntent(1, flag)  // Gets the PendingIntent for the click action
        }

        // Builds the notification
        return NotificationCompat.Builder(context, "Main Channel ID")
            .setContentTitle("Welcome")  // Title of the notification
            .setContentText("YouTube Channel: Stevdza-San")  // Text content of the notification
            .setSmallIcon(R.drawable.ic_baseline_notifications_24)  // Small icon displayed in the notification
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)  // Notification priority
            .setVisibility(VISIBILITY_PRIVATE)  // Visibility settings for the notification
            .setPublicVersion(
                NotificationCompat.Builder(context, "Main Channel ID")
                    .setContentTitle("Hidden")  // Title for the public version of the notification
                    .setContentText("Unlock to see the message.")  // Text for the public version of the notification
                    .build()
            )
            .addAction(0, "ACTION", pendingIntent)  // Action button with the PendingIntent
            .setContentIntent(clickPendingIntent)  // PendingIntent triggered when the notification is clicked
    }

    // Provides a singleton instance of NotificationManagerCompat
    @Singleton
    @Provides
    fun provideNotificationManager(
        @ApplicationContext context: Context
    ): NotificationManagerCompat {
        val notificationManager = NotificationManagerCompat.from(context)  // Gets the NotificationManagerCompat instance
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {  // Checks if the OS version is Oreo or above
            val channel = NotificationChannel(
                "Main Channel ID",  // Channel ID
                "Main Channel",  // Channel name
                NotificationManager.IMPORTANCE_DEFAULT  // Channel importance level
            )
            notificationManager.createNotificationChannel(channel)  // Creates the notification channel
        }
        return notificationManager  // Returns the NotificationManagerCompat instance
    }

    @Singleton
    @Provides
    fun provideTodoSearchManager(@ApplicationContext context: Context)= TodoSearchManager(context)

}