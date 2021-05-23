package com.example.stepcounter.fragment

import android.Manifest
import android.content.Context
import android.content.Context.SENSOR_SERVICE
import android.content.pm.PackageManager
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.getSystemService
import com.example.stepcounter.R
import com.example.stepcounter.databinding.FragmentStartBinding
import kotlin.math.sqrt


class StartFragment : Fragment() {
    private lateinit var binding: FragmentStartBinding
    private lateinit var sensorManager: SensorManager
    private lateinit var sensor: Sensor
    private var magnitudePrevious = 0.0
    private var stepCount = 0

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        binding = FragmentStartBinding.inflate(inflater, container, false)

        sensorManager = activity?.getSystemService(SENSOR_SERVICE) as SensorManager
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)

        val stepDetector = object : SensorEventListener {
            override fun onSensorChanged(event: SensorEvent?) {
                if (event != null) {
                    val x_acceleration = event.values[0]
                    val y_acceleration = event.values[1]
                    val z_acceleration = event.values[2]

                    val magnitude =
                            sqrt(x_acceleration * x_acceleration + y_acceleration * y_acceleration + z_acceleration * z_acceleration)
                    val delta = magnitude - magnitudePrevious;
                    magnitudePrevious = magnitude.toDouble()

                    if (delta > 6){
                        stepCount++
                    }
                    binding.tvCountSteps.text = stepCount.toString()
                }
            }

            override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {

            }
        }
        sensorManager.registerListener(stepDetector, sensor, SensorManager.SENSOR_DELAY_NORMAL);

        return binding.root
    }




}