package com.example.obstaclerace.models

import com.example.obstaclerace.utilities.Constants

data class GameRecord private constructor(
    val coins: Long,
    val distance: Long,
    val gameMode: String?,
    val locationLat: Double,
    val locationLon: Double,
    val timestamp: Long
) {

    class Builder(
        var coins: Long = 0L,
        var distance: Long = 0L,
        var gameMode: String? = null,
        var locationLat: Double = Constants.LocationDefault.LATITUDE,
        var locationLon: Double = Constants.LocationDefault.LONGITUDE,
        var timestamp: Long = 0L
    ) {
        fun coins(coins: Long) = apply { this.coins = coins }
        fun distance(distance: Long) = apply { this.distance = distance }
        fun gameMode(gameMode: String?) = apply { this.gameMode = gameMode }
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
