package com.compose.experiment.workmanager.screen

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.lifecycle.LifecycleOwner
import androidx.work.Data
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.compose.experiment.workmanager.worker.CounterWorker

@Composable
fun CounterWorkerScreen(context: Context, lifecycleOwner: LifecycleOwner){
    val initialWorkRequest = OneTimeWorkRequestBuilder<CounterWorker>()
        .setInputData(
            Data.Builder().putInt("COUNTER_VALUE", 0).build()
        ).build()

    WorkManager.getInstance(context).enqueue(initialWorkRequest)
}