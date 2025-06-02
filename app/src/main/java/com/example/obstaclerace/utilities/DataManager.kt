package com.example.obstaclerace.utilities

import com.example.obstaclerace.models.GameRecord

class DataManager private constructor() {
    private val gameRecords: MutableList<GameRecord> = emptyList<GameRecord>().toMutableList()

    companion object {
        @Volatile
        private var instance: DataManager? = null

        fun init(): DataManager {
            return instance ?: synchronized(this) {
                instance ?: DataManager().also { instance = it }
            }
        }

        fun getInstance(): DataManager {
            return instance
                ?: throw IllegalStateException("DataManager must be initialized by calling init() before use.")
        }
    }

    fun getTopRecords(): List<GameRecord> {
        val list = gameRecords.toSortedSet(compareBy { it.coins }).toList()

        if (list.isEmpty() || list.size < Constants.GameRecords.TOP_COUNT)
            return list

        return list.subList(0, Constants.GameRecords.TOP_COUNT)
    }

    fun addGameRecord(record: GameRecord) {
        gameRecords.add(record)
    }
}