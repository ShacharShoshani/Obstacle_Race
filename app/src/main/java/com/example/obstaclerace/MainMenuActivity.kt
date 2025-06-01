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

class MainMenuActivity : AppCompatActivity() {
    private lateinit var game_mode_SWITCH: SwitchCompat
    private lateinit var init_game_BTN: Button
    private lateinit var display_records_BTN: Button

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
        display_records_BTN = findViewById(R.id.display_records_BTN)
    }

    private fun initViews() {
        init_game_BTN.setOnClickListener { _: View -> initGame() }
        display_records_BTN.setOnClickListener { _: View -> displayRecords() }
    }

    private fun displayRecords() {
        Intent(this, RecordsActivity::class.java).apply {
            startActivity(this)
        }
    }

    private fun initGame() {
        Intent(this, MainActivity::class.java).apply {
            putExtra(R.string.param_useButtons.toString(), game_mode_SWITCH.isChecked)
            startActivity(this)
        }
    }
}