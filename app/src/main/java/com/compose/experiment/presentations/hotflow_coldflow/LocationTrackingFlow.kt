package com.compose.experiment.presentations.hotflow_coldflow

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import androidx.core.content.ContextCompat
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow


fun observeLocation(context: Context): Flow<Location> {
    if (!context.hasLocationPermission()) {
        throw SecurityException("Missing location permission")
    }
    return callbackFlow {
        val client = LocationServices.getFusedLocationProviderClient(context)
        val request = LocationRequest.Builder(Priority.PRIORITY_HIGH_ACCURACY, 5000L).build()
        val callback = object : LocationCallback() {
            override fun onLocationResult(result: com.google.android.gms.location.LocationResult) {
                super.onLocationResult(result)
                println("Location callback triggered")
                result.locations.lastOrNull()?.let { location ->
                    trySend(location).isSuccess
                }
            }
        }

        try {
            client.requestLocationUpdates(request, callback, context.mainLooper)
        } catch (e: SecurityException) {
            close(e) // Closes the Flow with an error
        }

        awaitClose {
            client.removeLocationUpdates(callback)
        }
    }
}

private fun Context.hasLocationPermission(): Boolean {
    return ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED ||
            ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED
}