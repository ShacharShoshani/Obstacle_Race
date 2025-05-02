package com.example.obstaclerace.logic

import android.content.Context
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import android.os.VibratorManager

class VibrationManager(private val context: Context) {
    private val vibrator: Vibrator

    init {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            val vibratorManager =
                context.getSystemService(Context.VIBRATOR_MANAGER_SERVICE) as VibratorManager
            vibrator = vibratorManager.defaultVibrator
        } else {
            vibrator = context.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
        }
    }

    fun vibrate() {
        if (!vibrator.hasVibrator())
            return

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            vibrator.vibrate(
                VibrationEffect.createOneShot(
                    500,
                    VibrationEffect.DEFAULT_AMPLITUDE
                )
            )
        } else {
            vibrator.vibrate(500)
        }
    }
}