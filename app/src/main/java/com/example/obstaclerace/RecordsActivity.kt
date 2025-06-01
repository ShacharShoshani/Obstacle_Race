package com.example.obstaclerace

import android.os.Bundle
import android.widget.FrameLayout
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.obstaclerace.interfaces.Callback_HighScoreClicked
import com.example.obstaclerace.ui.HighScoresFragment
import com.example.obstaclerace.ui.MapFragment

class RecordsActivity : AppCompatActivity() {


    private lateinit var main_FRAME_highScores: FrameLayout

    private lateinit var main_FRAME_map: FrameLayout

    private lateinit var mapFragment: MapFragment
    private lateinit var highScoresFragment: HighScoresFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_records)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main_records)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        findViews()
        initViews()
    }

    private fun findViews() {
        main_FRAME_map = findViewById(R.id.main_FRAME_map)
        main_FRAME_highScores = findViewById(R.id.main_FRAME_highScores)
    }

    private fun initViews() {
        mapFragment = MapFragment()
        supportFragmentManager
            .beginTransaction()
            .add(R.id.main_FRAME_map, mapFragment)
            .commit()

        highScoresFragment = HighScoresFragment()
        highScoresFragment.highScoreItemClicked =
            object : Callback_HighScoreClicked {
                override fun highScoreItemClicked(lat: Double, lon: Double) {
                    mapFragment.zoom(lat, lon)
                }
            }
        supportFragmentManager
            .beginTransaction()
            .add(R.id.main_FRAME_highScores, highScoresFragment)
            .commit()
    }
}