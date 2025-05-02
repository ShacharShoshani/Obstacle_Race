package com.example.obstaclerace.logic

import com.example.obstaclerace.utilities.Constants

class GameManager() {
    var playerColumn: Int = Constants.PlayerPosition.DEFAULT
        private set

    var asteroidHeight: Int = Constants.AsteroidHeight.MAX
        private set

    fun movePlayer(left: Boolean = false) {
        if (left && playerColumn > Constants.PlayerPosition.MIN)
            playerColumn--
        else if (!left && playerColumn < Constants.PlayerPosition.MAX)
            playerColumn++
    }

    fun moveAsteroid() {
        if (asteroidHeight <= Constants.AsteroidHeight.MIN)
            asteroidHeight = Constants.AsteroidHeight.MAX
        else
            asteroidHeight -= 100
    }
}