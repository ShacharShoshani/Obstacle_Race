package com.example.obstaclerace.ui

import android.Manifest
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import com.example.obstaclerace.R
import com.example.obstaclerace.interfaces.Callback_HighScoreClicked
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices


class HighScoresFragment : Fragment() {


    private lateinit var highScores_ET_text: TextInputEditText

    private lateinit var highScores_BTN_send: MaterialButton

    private lateinit var fusedLocationClient: FusedLocationProviderClient

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
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this.requireContext())

        if (ActivityCompat.checkSelfPermission(
                this.requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this.requireContext(),
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            highScores_ET_text.setText(buildString {
                append(0)
                append(',')
                append(0)
            })
        }

        fusedLocationClient.lastLocation.addOnSuccessListener { location: Location? ->
            highScores_ET_text.setText(buildString {
                append(location?.latitude)
                append(',')
                append(location?.longitude)
            })
        }
    }

    private fun initViews() {
        highScores_BTN_send.setOnClickListener { _: View ->
            goToCoordinates()
        }
    }

    private fun goToCoordinates() {
        val coordinates = highScores_ET_text.text?.split(",")
        val lat: Double = coordinates?.getOrNull(0)?.toDoubleOrNull() ?: 0.0
        val lon: Double = coordinates?.getOrNull(1)?.toDoubleOrNull() ?: 0.0

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