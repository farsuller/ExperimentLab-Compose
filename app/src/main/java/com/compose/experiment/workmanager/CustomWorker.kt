package com.compose.experiment.workmanager

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import kotlinx.coroutines.delay

// CustomWorker class extends CoroutineWorker to perform background work using coroutines.
class CustomWorker(context: Context, workerParams: WorkerParameters) :
    CoroutineWorker(context, workerParams) {

    // The main work to be done in the background is defined in the doWork method.
    override suspend fun doWork(): Result {

        // Simulates a delay of 10 seconds to mimic some long-running operation.
        delay(10000)

        // Logs a message indicating that the worker has executed.
        Log.d("CustomWorker", "Worker executed")

        // Returns a success result, indicating that the work finished successfully.
        return Result.success()

        // Other possible results include:

        // Return this if the work fails and should not be retried.
        // return Result.failure()

        // Return this if the work fails and should be retried at a later time.
        // return Result.retry()
    }
}