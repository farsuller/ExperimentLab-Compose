package com.compose.experiment.workmanager

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.net.HttpURLConnection
import java.net.URL

/**
 * Sample Usage Scenario:
 *
 * Imagine you are developing an app that needs to periodically upload logs or sync data with a server.
 * You want to ensure that this task only runs when the device is connected to an unmetered network
 * (such as Wi-Fi) and is charging, to avoid consuming the user's mobile data or battery unnecessarily.
 *
 * WorkManager is the perfect solution for this use case because:
 * 1. It supports constraints like network type and charging status.
 * 2. It handles execution even if the app is closed or the device is rebooted.
 * 3. It provides a flexible retry policy in case of failure.
 *
 * In this scenario, you would use WorkManager to schedule a periodic task to upload logs, as shown in the code above.
 */

class AnalyticsWorker(context: Context, workerParams: WorkerParameters) :
    CoroutineWorker(context, workerParams) {

    override suspend fun doWork(): Result {
        return withContext(Dispatchers.IO) {
            try {
                // Simulate sending JSON data to a server
                val url = URL("https://example.com/upload-analytics")
                val urlConnection = url.openConnection() as HttpURLConnection

                // JSON payload to be sent
                val jsonPayload = JSONObject()
                jsonPayload.put("event", "app_open")
                jsonPayload.put("timestamp", System.currentTimeMillis())

                // Setup the request
                urlConnection.requestMethod = "POST"
                urlConnection.setRequestProperty("Content-Type", "application/json")
                urlConnection.doOutput = true

                // Write the JSON data to the output stream
                urlConnection.outputStream.use { outputStream ->
                    outputStream.write(jsonPayload.toString().toByteArray())
                }

                // Get the response code
                val responseCode = urlConnection.responseCode
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    Log.d("AnalyticsWorker", "Analytics data uploaded successfully")
                    Result.success()
                } else {
                    Log.e("AnalyticsWorker", "Failed to upload analytics data")
                    Result.retry()
                }
            } catch (e: Exception) {
                Log.e("AnalyticsWorker", "Exception during upload", e)
                Result.retry()
            }
        }
    }
}