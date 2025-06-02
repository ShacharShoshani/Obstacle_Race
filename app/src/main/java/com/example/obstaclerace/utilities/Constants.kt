package com.example.obstaclerace.utilities

class Constants {
    object PlayerPosition {
        const val MIN = 0
        const val DEFAULT = 0
        const val MAX = 4
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
        const val SAVING_RECORD = "Saving new record..."
        const val RECORD_SAVED_SUCCESS = "Record saved successfully."
    }

    object GameMode {
        const val BUTTONS = "BUTTONS"
        const val TILT = "TILT"
    }

    object GameRecords {
        const val TOP_COUNT = 10
    }

    object LocationDefault {
        const val LONGITUDE = 0.0
        const val LATITUDE = 0.0
    }
}