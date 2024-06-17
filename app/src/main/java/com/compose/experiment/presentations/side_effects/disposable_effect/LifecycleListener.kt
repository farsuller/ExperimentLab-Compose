package com.compose.experiment.presentations.side_effects.disposable_effect

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.LocalLifecycleOwner

@Composable
fun DisposableEffectScreen(){
    var screen by remember { mutableStateOf("A") }
    
    
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center){
        Button(onClick = { screen = if(screen == "B") "A" else "B" }) {
            Text(text = "Change Screen to ${if(screen == "A") "B" else "A"}")
        }
    }

    when(screen){
        "A" -> ScreenA()
        "B" -> ScreenB()
    }
}

@Composable()
fun ScreenA(){
    LifecycleListener(
        screenName = "ScreenA",
        onResume = {
            Log.d("LifecycleListenerScreenA", "onResume ScreenA")
        }
    )
}

@Composable()
fun ScreenB(){
    LifecycleListener(
        screenName = "ScreenB",
        onResume = {
            Log.d("LifecycleListener", "onResume ScreenB")
        }
    )
}


@Composable
fun LifecycleListener(
    screenName: String? = null,
    onCreate: (() -> Unit)? = null,
    onStart: (() -> Unit)? = null,
    onResume: (() -> Unit)? = null,
    onPause: (() -> Unit)? = null,
    onStop: (() -> Unit)? = null,
    onDestroy: (() -> Unit)? = null,
    onAny: (() -> Unit)? = null,
) {
    val lifecycleOwner = LocalLifecycleOwner.current

    //DisposableEffect is used to clean up the observer when the composable is disposed
    //or when the lifecycleOwner changes
    //the difference from LaunchedEffect is that LaunchedEffect will only be called once but
    // DisposableEffect will be called every time the composable is disposed
    DisposableEffect(lifecycleOwner) {
        val lifecycleObserver = LifecycleEventObserver { _, event ->
            when(event) {
                Lifecycle.Event.ON_CREATE -> onCreate?.invoke()
                Lifecycle.Event.ON_START -> onStart?.invoke()
                Lifecycle.Event.ON_RESUME -> onResume?.invoke()
                Lifecycle.Event.ON_PAUSE -> onPause?.invoke()
                Lifecycle.Event.ON_STOP -> onStop?.invoke()
                Lifecycle.Event.ON_DESTROY -> onDestroy?.invoke()
                Lifecycle.Event.ON_ANY -> onAny?.invoke()
            }
        }
        lifecycleOwner.lifecycle.addObserver(lifecycleObserver)

        //onDispose is called when the composable is disposed
        onDispose {
            Log.d("LifecycleListener", "onDispose Observer $screenName")
            lifecycleOwner.lifecycle.removeObserver(lifecycleObserver)
        }
    }
}