package com.example.stepcounter

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.stepcounter.databinding.ActivitySettingBinding
import com.example.stepcounter.fragment.HomeFragment
import com.example.stepcounter.fragment.MyPreferenceFragment

class SettingActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySettingBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val mFragmentManager = supportFragmentManager
        val mFragmentSetting = MyPreferenceFragment()

        mFragmentManager.beginTransaction().apply {
            replace(R.id.frame_container, mFragmentSetting, MyPreferenceFragment::class.java.simpleName)
            commit()
        }

        binding.ivBack.setOnClickListener {
            onBackPressed()
        }
    }

}