package com.example.stepcounter

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.stepcounter.databinding.ActivityMainBinding
import com.example.stepcounter.fragment.HomeFragment

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val mFragmentManager = supportFragmentManager
        val mFragmentHome = HomeFragment()

        mFragmentManager.beginTransaction().apply {
            replace(R.id.frame_container, mFragmentHome, HomeFragment::class.java.simpleName)
            addToBackStack(null)
            commit()
        }
    }
}