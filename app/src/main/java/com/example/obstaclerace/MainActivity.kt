package com.example.obstaclerace

import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.isVisible
import androidx.core.view.updateMarginsRelative
import com.example.obstaclerace.interfaces.TiltCallback
import com.example.obstaclerace.logic.GameManager
import com.example.obstaclerace.logic.VibrationManager
import com.example.obstaclerace.utilities.Constants
import com.example.obstaclerace.utilities.SingleSoundPlayer
import com.example.obstaclerace.utilities.TiltDetector
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {
    private lateinit var main_IMG_hearts: Array<AppCompatImageView>
    private lateinit var left_BTN: FloatingActionButton
    private lateinit var right_BTN: FloatingActionButton
    private lateinit var player: AppCompatImageView
    private lateinit var coinCountLabel: TextView
    private lateinit var odometerLabel: TextView
    private lateinit var asteroids: Array<AppCompatImageView>
    private lateinit var coins: Array<AppCompatImageView>

    private lateinit var gameManager: GameManager
    private lateinit var vibrationManager: VibrationManager
    private lateinit var timer: CountDownTimer
    private lateinit var toast: Toast
    private lateinit var tiltDetector: TiltDetector
    private lateinit var singleSoundPlayer: SingleSoundPlayer

    private val playerPosition: IntArray = IntArray(2)
    private val asteroidPosition: IntArray = IntArray(2)
    private val coinPosition: IntArray = IntArray(2)

    private var isInButtonsMode: Boolean = true

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
        initApplication()
        gameLoop()
    }

    override fun onPause() {
        super.onPause()
        timer.cancel()
    }

    override fun onRestart() {
        super.onRestart()
        timer.start()
    }

    private fun findViews() {
        left_BTN = findViewById(R.id.left_BTN)
        right_BTN = findViewById(R.id.right_BTN)
        player = findViewById(R.id.player_IMG)
        main_IMG_hearts = arrayOf(
            findViewById(R.id.main_IMG_heart0),
            findViewById(R.id.main_IMG_heart1),
            findViewById(R.id.main_IMG_heart2)
        )
        asteroids = arrayOf(
            findViewById(R.id.asteroid_IMG_1),
            findViewById(R.id.asteroid_IMG_2),
            findViewById(R.id.asteroid_IMG_3),
            findViewById(R.id.asteroid_IMG_4),
            findViewById(R.id.asteroid_IMG_5)

        )
        coins = arrayOf(
            findViewById(R.id.coin_IMG_1),
            findViewById(R.id.coin_IMG_2),
            findViewById(R.id.coin_IMG_3)
        )
        coinCountLabel = findViewById(R.id.coin_count_LBL)
        odometerLabel = findViewById(R.id.odometer_LBL)
    }

    private fun initApplication() {
        gameManager = GameManager(main_IMG_hearts.size)
        vibrationManager = VibrationManager(this)
        singleSoundPlayer = SingleSoundPlayer(this)
        isInButtonsMode = intent.getBooleanExtra(R.string.param_useButtons.toString(), false)
        initViews()

        if (!isInButtonsMode)
            initTiltDetector()
    }

    private fun initTiltDetector() {
        tiltDetector = TiltDetector(
            context = this,
            tiltCallback = object : TiltCallback {
                override fun tiltX() {
                    tiltDetector.tiltX.also {
                        if (it != 0f) {
                            gameManager.movePlayer(it > 0)
                            refreshPlayer()
                        }
                    }
                }

                override fun tiltY() {

                }
            }
        )
    }

    private fun initViews() {
        if (isInButtonsMode) {
            left_BTN.setOnClickListener { _: View -> moveClick(true) }
            right_BTN.setOnClickListener { _: View -> moveClick() }
        }else{
            disableButton(left_BTN)
            disableButton(right_BTN)
        }

        toast = Toast(this)
        toast.duration = Toast.LENGTH_LONG
    }

    private fun disableButton(button: FloatingActionButton) {
        button.isVisible = false
        button.isClickable = false
    }

    private fun moveClick(left: Boolean = false) {
        gameManager.movePlayer(left)
        refreshPlayer()
    }

    private fun refreshLifeCount() {
        if (gameManager.crashCount > 0)
            main_IMG_hearts[main_IMG_hearts.size - gameManager.crashCount].visibility =
                View.INVISIBLE
        else
            for (heart in main_IMG_hearts)
                heart.visibility = View.VISIBLE
    }

    private fun refreshAsteroids() {
        for (i in asteroids.indices) {
            val layoutParams: RelativeLayout.LayoutParams =
                RelativeLayout.LayoutParams(asteroids[i].layoutParams)

            layoutParams.marginStart = getMarginStart(i)
            layoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM)
            layoutParams.bottomMargin =
                gameManager.asteroidHeight - Constants.AsteroidHeight.STEP_OFFSET * ((i + 1) % 2)

            asteroids[i].layoutParams = layoutParams
        }
    }

    private fun refreshCoins() {
        val offset = Constants.AsteroidHeight.STEP_OFFSET
        for (coin in coins) {
            val layoutParams: RelativeLayout.LayoutParams =
                RelativeLayout.LayoutParams(coin.layoutParams)

            layoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM)

            if (coin.id == R.id.coin_IMG_2) {
                layoutParams.addRule(RelativeLayout.CENTER_IN_PARENT)
            } else if (coin.id == R.id.coin_IMG_3) {
                layoutParams.addRule(RelativeLayout.ALIGN_PARENT_END)
            }

            layoutParams.bottomMargin = gameManager.asteroidHeight + offset

            coin.layoutParams = layoutParams
        }
    }

    private fun refreshPlayer() {
        val layoutParams: RelativeLayout.LayoutParams =
            RelativeLayout.LayoutParams(player.layoutParams)

        layoutParams.marginStart = getMarginStart(gameManager.playerColumn)

        layoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM)
        layoutParams.updateMarginsRelative(bottom = 250)
        player.layoutParams = layoutParams
    }

    private fun getMarginStart(offsetFactor: Int): Int {
        return resources.getDimensionPixelSize(R.dimen.game_item_margin_start) +
                offsetFactor * resources.getDimensionPixelSize(R.dimen.game_item_margin_offset)
    }

    private fun gameLoop() {
        if (!isInButtonsMode)
            tiltDetector.start()

        timer = object : CountDownTimer(60000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                gameManager.moveAsteroids()
                refreshAsteroids()
                refreshCoins()
                checkIfPlayerCrashed()
                tryToCollectCoins()
                odometerLabel.text = gameManager.distance.toString()
            }

            override fun onFinish() {
                timer.start()
            }

        }
        timer.start()
    }

    private fun tryToCollectCoins() {
        val collectedCoin = collectCoin() ?: return
        collectedCoin.isVisible = false
        gameManager.increaseCoinCount()
        coinCountLabel.text = gameManager.coinCount.toString()
    }

    private fun collectCoin(): AppCompatImageView? {
        var yDiff: Int
        var xDiff: Int

        for (coin in coins) {
            player.getLocationOnScreen(playerPosition)
            coin.getLocationOnScreen(coinPosition)
            yDiff = playerPosition[1] - coinPosition[1]
            xDiff = playerPosition[0] - coinPosition[0]

            if (coin.isVisible &&
                yDiff >= -Constants.PlayerCoinOverlap.VERTICAL_DISTANCE
                && yDiff <= Constants.PlayerCoinOverlap.VERTICAL_DISTANCE
                && xDiff >= -Constants.PlayerCoinOverlap.HORIZONTAL_DISTANCE
                && xDiff <= Constants.PlayerCoinOverlap.HORIZONTAL_DISTANCE
            )
                return coin
            else {
                coin.isVisible = true
            }
        }

        return null
    }

    private fun checkIfPlayerCrashed() {
        if (!isCrashed())
            return
        else {
            respondToCrash()
        }

    }

    private fun isCrashed(): Boolean {
        var yDiff: Int

        for (asteroid in asteroids) {
            player.getLocationOnScreen(playerPosition)
            asteroid.getLocationOnScreen(asteroidPosition)
            yDiff = playerPosition[1] - asteroidPosition[1]

            if (yDiff >= -Constants.PlayerAsteroidOverlap.VERTICAL_DISTANCE
                && yDiff <= Constants.PlayerAsteroidOverlap.VERTICAL_DISTANCE
                && playerPosition[0] - asteroidPosition[0] == 0
            )
                return true
        }

        return false
    }

    private fun respondToCrash() {
        gameManager.respondToCrash()
        refreshLifeCount()
        coinCountLabel.text = gameManager.coinCount.toString()
        displayCrashMessage()
        singleSoundPlayer.playSound(R.raw.boom)
        vibrationManager.vibrate()
    }

    private fun displayCrashMessage() {
        val text: String =
            if (gameManager.crashCount > 0)
                Constants.Toast.CRASH_MESSAGE
            else Constants.Toast.GAME_OVER

        toast.cancel()
        toast.setText(text)
        toast.show()
    }
}