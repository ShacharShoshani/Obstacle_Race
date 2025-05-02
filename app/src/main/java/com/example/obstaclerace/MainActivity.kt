package com.example.obstaclerace

import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.widget.RelativeLayout
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updateMarginsRelative
import com.example.obstaclerace.logic.GameManager
import com.example.obstaclerace.utilities.Constants
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {
    private lateinit var main_IMG_hearts: Array<AppCompatImageView>
    private lateinit var left_BTN: FloatingActionButton
    private lateinit var right_BTN: FloatingActionButton
    private lateinit var player: AppCompatImageView
    private lateinit var asteroid: AppCompatImageView

    private lateinit var gameManager: GameManager
    private lateinit var timer: CountDownTimer
    private lateinit var toast: Toast

    private val playerPosition: IntArray = IntArray(2)
    private val asteroidPosition: IntArray = IntArray(2)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        findViews()
        gameManager = GameManager(main_IMG_hearts.size)
        initViews()
        toast = Toast(this)
        toast.duration = Toast.LENGTH_SHORT

        timer = object : CountDownTimer(60000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                gameManager.moveAsteroid()
                refreshAsteroid()

                checkIfPlayerCrashed()
            }

            override fun onFinish() {
                timer.start()
            }

        }
        timer.start()
    }

    private fun checkIfPlayerCrashed() {
        if (!isCrashed())
            return
        else {
            respondToCrash()
        }

    }

    private fun respondToCrash() {
        gameManager.updateCrashCount()
        refreshLifeCount()

        dispalyCrashMessage()

    }

    private fun dispalyCrashMessage() {
        val text: String =
            if (gameManager.crashCount > 0)
                "The spaceship hit an asteroid!"
            else "Game Over. Restarting"

        toast.cancel()
        toast.setText(text)
        toast.show()
    }

    private fun refreshLifeCount() {
        if (gameManager.crashCount > 0)
            main_IMG_hearts[main_IMG_hearts.size - gameManager.crashCount].visibility =
                View.INVISIBLE
        else
            for (heart in main_IMG_hearts)
                heart.visibility = View.VISIBLE
    }

    private fun isCrashed(): Boolean {
        player.getLocationOnScreen(playerPosition)
        asteroid.getLocationOnScreen(asteroidPosition)
        val yDiff = playerPosition[1] - asteroidPosition[1]

        return yDiff >= -Constants.PlayerAsteroidOverlap.VERTICAL_DISTANCE
                && yDiff <= Constants.PlayerAsteroidOverlap.VERTICAL_DISTANCE
                && playerPosition[0] - asteroidPosition[0] == 0
    }

    private fun initViews() {
        left_BTN.setOnClickListener { _: View -> moveClick(true) }
        right_BTN.setOnClickListener { _: View -> moveClick() }
        refreshPlayer()
        refreshAsteroid()
    }

    private fun refreshAsteroid() {
        val layoutParams: RelativeLayout.LayoutParams =
            RelativeLayout.LayoutParams(asteroid.layoutParams)

        layoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM)
        layoutParams.bottomMargin = gameManager.asteroidHeight

        asteroid.layoutParams = layoutParams
    }

    private fun moveClick(left: Boolean = false) {
        gameManager.movePlayer(left)
        refreshPlayer()
    }

    private fun refreshPlayer() {
        val layoutParams: RelativeLayout.LayoutParams =
            RelativeLayout.LayoutParams(player.layoutParams)

        when (gameManager.playerColumn) {
            0 -> {
                layoutParams.addRule(RelativeLayout.ALIGN_PARENT_START)
                layoutParams.removeRule(RelativeLayout.CENTER_IN_PARENT)
                layoutParams.removeRule(RelativeLayout.ALIGN_PARENT_END)
            }

            1 -> {
                layoutParams.addRule(RelativeLayout.CENTER_IN_PARENT)
                layoutParams.removeRule(RelativeLayout.ALIGN_PARENT_START)
                layoutParams.removeRule(RelativeLayout.ALIGN_PARENT_END)
            }

            else -> {
                layoutParams.addRule(RelativeLayout.ALIGN_PARENT_END)
                layoutParams.removeRule(RelativeLayout.CENTER_IN_PARENT)
                layoutParams.removeRule(RelativeLayout.ALIGN_PARENT_START)
            }
        }

        layoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM)
        layoutParams.updateMarginsRelative(bottom = 250)
        player.layoutParams = layoutParams
    }

    private fun findViews() {
        left_BTN = findViewById(R.id.left_BTN)
        right_BTN = findViewById(R.id.right_BTN)
        player = findViewById(R.id.player_IMG)
        asteroid = findViewById(R.id.asteroid_IMG)
        main_IMG_hearts = arrayOf(
            findViewById(R.id.main_IMG_heart0),
            findViewById(R.id.main_IMG_heart1),
            findViewById(R.id.main_IMG_heart2)
        )
    }
}