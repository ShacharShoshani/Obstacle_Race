package com.example.obstaclerace.utilities

class Constants {
    object PlayerPosition {
        const val MIN = 0
        const val DEFAULT = 1
        const val MAX = 2
    }

    object AsteroidHeight {
        const val MAX = 2000
        const val MIN = -75
        const val STEP = 100
        const val STEP_OFFSET = 400
    }

    object PlayerAsteroidOverlap {
        const val VERTICAL_DISTANCE = 200
    }

    object PlayerCoinOverlap {
        const val VERTICAL_DISTANCE = 100
        const val HORIZONTAL_DISTANCE = 100
    }

    object Toast {
        const val CRASH_MESSAGE = "The spaceship hit an asteroid!"
        const val GAME_OVER = "Game Over. Restarting"
    }
}