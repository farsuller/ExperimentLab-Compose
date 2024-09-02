package com.compose.experiment.presentations.custom_worker

import android.content.Context
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Box

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.work.BackoffPolicy
import androidx.work.Constraints
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.NetworkType
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.compose.experiment.utils.workmanager.CustomWorker
import kotlinx.coroutines.delay
import java.time.Duration
import java.util.concurrent.TimeUnit

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun CustomWorkerScreen(applicationContext: Context) {
    val lifecycleOwner = LocalLifecycleOwner.current

    var workerText by remember {
        mutableStateOf("")
    }

    // Defines constraints for the work request.
    // - Requires an unmetered network (like Wi-Fi).
    // - Requires the device to be charging.
    val constraints = Constraints.Builder()
        .setRequiredNetworkType(NetworkType.UNMETERED)
        .setRequiresCharging(true)
        .build()

    LaunchedEffect(key1 = Unit) {


        // This block of code will execute when the composable enters the composition.
        // Creates a PeriodicWorkRequest that schedules CustomWorker to run every 1 hour
        // with a flex interval of 15 minutes. This means the work can run at any time
        // in the 15 minutes before or after the 1-hour mark.
        val workRequest = PeriodicWorkRequestBuilder<CustomWorker>(
            repeatInterval = 1,
            repeatIntervalTimeUnit = TimeUnit.HOURS,
            flexTimeInterval = 15,
            flexTimeIntervalUnit = TimeUnit.MINUTES
        ).setBackoffCriteria(
            // Sets the backoff policy to linear with a 15-second delay in case of retryable failure.
            backoffPolicy = BackoffPolicy.LINEAR,
            duration = Duration.ofSeconds(15)
        )
            // Applies the defined constraints to the work request.
            .setConstraints(constraints)
            .build()

        // Retrieves an instance of WorkManager associated with the application's context.
        val workManager = WorkManager.getInstance(applicationContext)

        // Using enqueueUniquePeriodicWork to schedule the periodic work request with a unique name "myCustomWorker".
        // This ensures that only one instance of the worker is running. If there's already an existing work with
        // the same name, it will be kept (not replaced), preventing duplicate workers.
        workManager.enqueueUniquePeriodicWork(
            "myCustomWorker",
            ExistingPeriodicWorkPolicy.KEEP,
            workRequest
        )

        // If we used workManager.enqueue(workRequest) instead, it would create a new worker instance
        // every time this code runs, potentially leading to multiple instances of the worker running concurrently.

        // Observe the state of the unique work "myCustomWorker" using LiveData.
        // This will allow tracking the status of the work request.
        workManager.getWorkInfosForUniqueWorkLiveData("myCustomWorker")
            .observe(lifecycleOwner) { workInfos ->
                workInfos.forEach { workInfo ->
                    // Logs the current state of each workInfo to the console.
                    Log.d("CustomWorker", "${workInfo.state}")
                    workerText = "${workInfo.state}"
                }
            }

        // Delay for 5 seconds before cancelling the unique work.
        delay(5000)

        // Cancels the unique work "myCustomWorker".
        // This stops the periodic work if it's currently running and prevents future executions.
        workManager.cancelUniqueWork("myCustomWorker")


    }
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text(text = "Worker status: $workerText")
    }
}