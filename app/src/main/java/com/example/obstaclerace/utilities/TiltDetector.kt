package com.example.obstaclerace.utilities

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import com.example.obstaclerace.interfaces.TiltCallback

class TiltDetector(context: Context, private var tiltCallback: TiltCallback?) {

    private val sensorManager = context
        .getSystemService(Context.SENSOR_SERVICE) as SensorManager

    private val sensor = sensorManager
        .getDefaultSensor(Sensor.TYPE_ACCELEROMETER) as Sensor

    private lateinit var sensorEventListener: SensorEventListener

    var tiltX: Float = 0f
        private set

    private var timestamp: Long = 0L

    init {
        initEventListener()
    }

    private fun initEventListener() {
        sensorEventListener = object : SensorEventListener {
            override fun onSensorChanged(event: SensorEvent) {
                val x = event.values[0]
                val y = event.values[1]
                calculateTilt(x, y)
            }

            override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
                // pass
            }

        }
    }

    private fun calculateTilt(x: Float, y: Float) {
        if (System.currentTimeMillis() - timestamp >= 500) {
            timestamp = System.currentTimeMillis()
            tiltX = x
            tiltCallback?.tiltX()
        }
    }

    fun start() {
        sensorManager
            .registerListener(
                sensorEventListener,
                sensor,
                SensorManager.SENSOR_DELAY_NORMAL
            )
    }

    fun stop() {
        sensorManager
            .unregisterListener(
                sensorEventListener,
                sensor
            )
    }
}