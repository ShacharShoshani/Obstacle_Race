package com.example.obstaclerace.utilities

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import android.location.Location
import com.example.obstaclerace.interfaces.LocationCallback
import com.google.android.gms.location.LocationServices

class MyLocationService(private val context: Context) {
    private val fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)

    var locationCallback: LocationCallback? = null

    fun lastLocation() {
        if (ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            locationCallback?.locationFailure(Exception("Location access was denied."))
        }

        fusedLocationClient.lastLocation.addOnSuccessListener { location: Location ->
            locationCallback?.locationSuccess(location)
        }

        fusedLocationClient.lastLocation.addOnFailureListener { e: Exception? ->
            locationCallback?.locationFailure(e)
        }
    }
}