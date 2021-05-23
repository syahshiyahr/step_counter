package com.example.stepcounter.fragment

import android.content.Context.SENSOR_SERVICE
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat.getSystemService
import com.example.stepcounter.R
import com.example.stepcounter.databinding.FragmentRunBinding
import kotlin.math.sqrt

class RunFragment : Fragment() {
    private lateinit var binding: FragmentRunBinding
    private lateinit var sensorManager: SensorManager
    private lateinit var sensor: Sensor
    private var magnitudePrevious = 0.0
    var stepCount = 0
    var targetStep = 0

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        binding = FragmentRunBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        if(arguments != null){
            val target = arguments?.getInt(ARG_TARGET)
            targetStep = target!!

            binding.tvTarget.text = "Target : ${targetStep}"
            binding.progressCircular.progressMax = targetStep.toFloat()
        }

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

                    binding.progressCircular.apply {
                        setProgressWithAnimation(stepCount.toFloat())
                    }

                    if(stepCount == targetStep){
                        val mFragmentManager = fragmentManager
                        val mFragmentReached = GoalsReachedFragment()

                        mFragmentManager?.beginTransaction()?.apply {
                            replace(R.id.frame_container, mFragmentReached, GoalsReachedFragment::class.java.simpleName)
                            addToBackStack(null)
                            commit()
                        }
                    }

                }
            }

            override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {

            }
        }
        sensorManager.registerListener(stepDetector, sensor, SensorManager.SENSOR_DELAY_NORMAL)

        Log.d("Target", targetStep.toString())


    }

    companion object {
        var ARG_TARGET = "target"
    }
}