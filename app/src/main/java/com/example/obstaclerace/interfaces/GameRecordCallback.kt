package com.example.obstaclerace.interfaces

import com.example.obstaclerace.models.GameRecord

interface GameRecordCallback {
    fun itemClicked(record: GameRecord, position: Int)
}