package com.example.obstaclerace.models

import com.google.android.gms.maps.model.LatLng
import java.time.Instant
import java.time.LocalDate

data class GameRecord private constructor(
    val coins: Long,
    val distance: Long,
    val gameMode: String,
    val locationLat: Double,
    val locationLon: Double,
    val timestamp: Long
) {

    class Builder(
        var coins: Long = 0L,
        var distance: Long = 0L,
        var gameMode: String = "",
        var locationLat: Double = 0.0,
        var locationLon: Double = 0.0,
        var timestamp: Long = 0L
    ) {
        fun coins(coins: Long) = apply { this.coins = coins }
        fun distance(distance: Long) = apply { this.distance = distance }
        fun gameMode(gameMode: String) = apply { this.gameMode = gameMode }
        fun locationLat(locationLat: Double) = apply { this.locationLat = locationLat }
        fun locationLon(locationLon: Double) = apply { this.locationLon = locationLon }
        fun timestamp(timestamp: Long) = apply { this.timestamp = timestamp }
        fun build() = GameRecord(
            coins,
            distance,
            gameMode,
            locationLat,
            locationLon,
            timestamp
        )
    }
}
