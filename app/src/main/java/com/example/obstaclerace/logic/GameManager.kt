package com.example.obstaclerace.logic

import com.example.obstaclerace.utilities.Constants

class GameManager(private val lifeCount: Int) {
    var playerColumn: Int = Constants.PlayerPosition.DEFAULT
        private set

    var asteroidHeight: Int = Constants.AsteroidHeight.MAX
        private set

    var crashCount: Int = 0
        private set

    var coinCount: Long = 0
        private set

    var distance: Long = 0
        private set

    fun increaseCoinCount() {
        coinCount++
    }

    fun movePlayer(left: Boolean = false) {
        if (left && playerColumn > Constants.PlayerPosition.MIN)
            playerColumn--
        else if (!left && playerColumn < Constants.PlayerPosition.MAX)
            playerColumn++
    }

    fun moveAsteroids() {
        if (asteroidHeight <= Constants.AsteroidHeight.MIN)
            asteroidHeight = Constants.AsteroidHeight.MAX
        else
            asteroidHeight -= Constants.AsteroidHeight.STEP

        distance += Constants.AsteroidHeight.STEP
    }

    /**
     * Return true if the player lost
     */
    fun respondToCrash(): Boolean {
        crashCount++

        return crashCount >= lifeCount
    }

    fun reset() {
        crashCount = 0
        coinCount = 0
        asteroidHeight = Constants.AsteroidHeight.MAX
        distance = 0
    }
}