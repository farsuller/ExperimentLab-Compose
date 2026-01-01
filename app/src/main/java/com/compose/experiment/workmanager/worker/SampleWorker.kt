package com.compose.experiment.workmanager.worker

import android.content.Context
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class SampleWorker(context : Context, params : WorkerParameters) : Worker(context = context, workerParams = params) {
    override fun doWork(): Result {
        val timestamp = System.currentTimeMillis()

        val currentTime = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(Date(timestamp))

        Log.d("WorkState", "Current time doWork: $currentTime")

        Thread.sleep(5000)

        return Result.success()
    }
}