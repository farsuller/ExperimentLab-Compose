package com.compose.experiment.workmanager.worker

import android.content.Context
import android.util.Log
import androidx.work.Data
import androidx.work.OneTimeWorkRequest
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.Worker
import androidx.work.WorkerParameters
import java.util.concurrent.TimeUnit

class CounterWorker(context: Context, workerParams: WorkerParameters) : Worker(context, workerParams) {
    override fun doWork(): Result {
        val counter = inputData.getInt("COUNTER_VALUE", 0)

        Log.d("CounterWorker","Counter $counter")

        val nextWorkRequest = createNextWorkRequest(counter + 1)
        WorkManager.getInstance(applicationContext).enqueue(nextWorkRequest)
        Log.d("CounterWorker","Running in Background with counter :$counter")

        return Result.success()
    }

    private fun createNextWorkRequest(nextCounterValue: Int) : OneTimeWorkRequest {
        val inputData = Data.Builder().putInt("COUNTER_VALUE", nextCounterValue).build()

        return OneTimeWorkRequestBuilder<CounterWorker>()
            .setInputData(inputData)
            .setInitialDelay(5, TimeUnit.SECONDS)
            .build()
    }
}