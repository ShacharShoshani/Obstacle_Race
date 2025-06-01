package com.example.obstaclerace

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SwitchCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainMenu : AppCompatActivity() {
    private lateinit var init_game_BTN: Button
    private lateinit var game_mode_SWITCH: SwitchCompat

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main_menu)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        findViews()
        initViews()
    }

    private fun findViews() {
        init_game_BTN = findViewById(R.id.init_game_BTN)
        game_mode_SWITCH = findViewById(R.id.game_mode_SWITCH)
    }

    private fun initViews() {
        init_game_BTN.setOnClickListener { _: View -> initGame() }
    }

    private fun initGame() {
        startActivity(Intent(this, MainActivity::class.java).apply {
            putExtra(R.string.param_useButtons.toString(), game_mode_SWITCH.isChecked)
        })
    }
}