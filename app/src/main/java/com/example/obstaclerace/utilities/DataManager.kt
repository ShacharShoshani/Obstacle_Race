package com.example.obstaclerace.utilities

import com.google.gson.Gson
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
        val list = gameRecords.toSortedSet(compareBy { (it.coins * gameModeFactor(it)) }).toList()

        if (list.isEmpty() || list.size < Constants.GameRecords.TOP_COUNT)
            return list

        return list.subList(0, Constants.GameRecords.TOP_COUNT)
    }

    private fun gameModeFactor(record: GameRecord): Long {
        return if (record.gameMode == Constants.GameMode.TILT)
            10L
        else 1L
    }

    fun addGameRecord(record: GameRecord) {
        gameRecords.add(record)
    }

    fun loadFromDisk() {
        if (gameRecords.isNotEmpty())
            return

        val gameRecordFromSP = SharedPreferencesManager.getInstance().getString(
            Constants.SPKeys.GAME_RECORDS_KEY, Constants.SPDefaultValues.GAME_RECORDS_DEFAULT
        )

        val gson = Gson()

        val regeneratedGameRecords = gson.fromJson(gameRecordFromSP, Array<GameRecord>::class.java)

        for (record in regeneratedGameRecords)
            gameRecords.add(record)
    }

    fun exportToDisk() {
        val gson = Gson()
        val gameRecordArr = gameRecords.toTypedArray()
        val gameRecordsJson = gson.toJson(gameRecordArr)

        SharedPreferencesManager.getInstance()
            .putString(Constants.SPKeys.GAME_RECORDS_KEY, gameRecordsJson)
    }
}