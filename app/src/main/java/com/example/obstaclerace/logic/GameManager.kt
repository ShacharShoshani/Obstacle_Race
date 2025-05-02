package com.example.obstaclerace.logic

class GameManager() {
    var playerColumn: Int = 1
        private set

    fun movePlayer(left: Boolean = false) {
        if (left && playerColumn > 0)
            playerColumn--
        else if (!left && playerColumn < 2)
            playerColumn++
    }
}