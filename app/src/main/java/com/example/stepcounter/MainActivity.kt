package com.example.stepcounter

import android.Manifest
import android.content.pm.PackageManager
import android.hardware.Sensor
import android.hardware.SensorManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.stepcounter.databinding.ActivityMainBinding
import com.example.stepcounter.fragment.HomeFragment

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val pm = packageManager
        if (pm.hasSystemFeature(PackageManager.FEATURE_SENSOR_ACCELEROMETER)) {
            // the awesome stuff here
            Toast.makeText(this, "Feature Available", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, "Feature not available", Toast.LENGTH_SHORT).show()
        }

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACTIVITY_RECOGNITION)
                != PackageManager.PERMISSION_GRANTED
        ) {
            // Permission is not granted
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                ActivityCompat.requestPermissions(
                        this,
                        arrayOf(Manifest.permission.ACTIVITY_RECOGNITION), 100
                )
            }
        }

        val mFragmentManager = supportFragmentManager
        val mFragmentHome = HomeFragment()

        mFragmentManager.beginTransaction().apply {
            replace(R.id.frame_container, mFragmentHome, HomeFragment::class.java.simpleName)
            commit()
        }
    }

    override fun onRequestPermissionsResult(
            requestCode: Int,
            permissions: Array<String>,
            grantResults: IntArray
    ) {
        when (requestCode) {
            100 -> {
                if (grantResults.isEmpty() || grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this, "Permission has been denied by user", Toast.LENGTH_SHORT)
                            .show()
                } else {
                    Toast.makeText(this, "Permission allowed by user", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finishAffinity()
    }
}