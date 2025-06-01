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


class HighScoresFragment : Fragment() {


    private lateinit var highScores_ET_text: TextInputEditText

    private lateinit var highScores_BTN_send: MaterialButton

    var highScoreItemClicked: Callback_HighScoreClicked? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(R.layout.fragment_high_scores, container, false)
        findViews(view)
        initViews(view)
        return view
    }

    private fun initViews(view: View) {
        highScores_BTN_send.setOnClickListener { v: View ->
            val coordinates = highScores_ET_text.text?.split(",")
            val lat: Double = coordinates?.getOrNull(0)?.toDoubleOrNull() ?: 0.0
            val lon: Double = coordinates?.getOrNull(1)?.toDoubleOrNull() ?: 0.0

            itemClicked(lat, lon)
        }
    }

    private fun itemClicked(lat: Double, lon: Double) {
        highScoreItemClicked?.highScoreItemClicked(lat, lon)
    }

    private fun findViews(view: View) {
        highScores_ET_text = view.findViewById(R.id.highScores_ET_text)
        highScores_BTN_send = view.findViewById(R.id.highScores_BTN_send)
    }
}