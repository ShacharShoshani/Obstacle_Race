package com.example.obstaclerace.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.obstaclerace.R
import com.example.obstaclerace.adapters.GameRecordAdapter
import com.example.obstaclerace.interfaces.Callback_HighScoreClicked
import com.example.obstaclerace.interfaces.GameRecordCallback
import com.example.obstaclerace.models.GameRecord
import com.example.obstaclerace.utilities.DataManager


class HighScoresFragment : Fragment() {
    private val gameRecordAdapter = GameRecordAdapter(DataManager.getInstance().getTopRecords())

    var highScoreItemClicked: Callback_HighScoreClicked? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view: View = inflater.inflate(R.layout.fragment_high_scores, container, false)
        gameRecordAdapter.gameRecordCallback = object : GameRecordCallback {
            override fun itemClicked(record: GameRecord, position: Int) {
                highScoreItemClicked?.highScoreItemClicked(record.locationLat, record.locationLon)
            }

        }
        return view
    }
}