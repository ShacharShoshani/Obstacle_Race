package com.example.obstaclerace.interfaces

import android.location.Location

interface LocationCallback {
    fun locationSuccess(location: Location?)
    fun locationFailure(exception: Exception?)
}