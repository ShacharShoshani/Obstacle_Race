package com.example.obstaclerace.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import com.example.obstaclerace.R
import com.example.obstaclerace.interfaces.Callback_HighScoreClicked
import com.example.obstaclerace.utilities.Constants
import com.example.obstaclerace.utilities.DataManager


class HighScoresFragment : Fragment() {
    private lateinit var highScores_ET_text: TextInputEditText

    private lateinit var highScores_BTN_send: MaterialButton

    var highScoreItemClicked: Callback_HighScoreClicked? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view: View = inflater.inflate(R.layout.fragment_high_scores, container, false)
        findViews(view)
        initLocation()
        initViews()
        return view
    }

    private fun initLocation() {
        val topRecordsList = DataManager.getInstance().getTopRecords()

        if (topRecordsList.isEmpty())
            highScores_ET_text.setText(buildString {
                append(0)
                append(',')
                append(0)
            })
        else
            highScores_ET_text.setText(buildString {
                append(topRecordsList[0].locationLat)
                append(',')
                append(topRecordsList[0].locationLon)
            })
    }

    private fun initViews() {
        highScores_BTN_send.setOnClickListener { _: View ->
            goToCoordinates()
        }
    }

    private fun goToCoordinates() {
        val coordinates = highScores_ET_text.text?.split(",")
        val lat: Double =
            coordinates?.getOrNull(0)?.toDoubleOrNull() ?: Constants.LocationDefault.LATITUDE
        val lon: Double =
            coordinates?.getOrNull(1)?.toDoubleOrNull() ?: Constants.LocationDefault.LONGITUDE

        itemClicked(lat, lon)
    }

    private fun itemClicked(lat: Double, lon: Double) {
        highScoreItemClicked?.highScoreItemClicked(lat, lon)
    }

    private fun findViews(view: View) {
        highScores_ET_text = view.findViewById(R.id.highScores_ET_text)
        highScores_BTN_send = view.findViewById(R.id.highScores_BTN_send)
    }
}