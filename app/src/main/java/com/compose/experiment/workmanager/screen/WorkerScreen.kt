package com.compose.experiment.workmanager.screen

import android.content.Context
import android.util.Log
import androidx.compose.runtime.Composable
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkInfo
import androidx.work.WorkManager
import com.compose.experiment.workmanager.worker.SampleWorker

@Composable
fun WorkerScreen(context : Context, lifecycleOwner : LifecycleOwner){


    val oneTimeWorkRequest = OneTimeWorkRequestBuilder<SampleWorker>().build()
    val workManager = WorkManager.getInstance(context)

    workManager.enqueue(oneTimeWorkRequest)
    workManager.getWorkInfoByIdLiveData(oneTimeWorkRequest.id).observe(lifecycleOwner, Observer{ work->
        work?.run {
            when(this.state){
                WorkInfo.State.ENQUEUED -> Log.d("WorkState","OneTimeWork is Enqueued")
                WorkInfo.State.RUNNING -> Log.d("WorkState","OneTimeWork is Running")
                WorkInfo.State.SUCCEEDED -> Log.d("WorkState","OneTimeWork is Succeeded")
                WorkInfo.State.FAILED -> Log.d("WorkState","OneTimeWork is Failed")
                WorkInfo.State.BLOCKED -> Log.d("WorkState","OneTimeWork is Blocked")
                WorkInfo.State.CANCELLED -> Log.d("WorkState","OneTimeWork is Cancelled")
            }
        }
    })

}