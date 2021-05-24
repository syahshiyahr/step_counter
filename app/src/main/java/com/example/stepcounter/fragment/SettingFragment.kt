package com.example.stepcounter.fragment

import android.content.SharedPreferences
import android.os.Bundle
import android.preference.PreferenceFragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CompoundButton
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.preference.PreferenceFragmentCompat
import com.example.stepcounter.AlarmReceiver
import com.example.stepcounter.databinding.FragmentSettingBinding


class SettingFragment : PreferenceFragmentCompat(), SharedPreferences.OnSharedPreferenceChangeListener {
    private lateinit var binding: FragmentSettingBinding
    private lateinit var alarmReceiver: AlarmReceiver
    private lateinit var SET: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentSettingBinding.inflate(inflater, container, false)

        isSwitchChecked()
        SET = "atur pengingat"

        return binding.root
    }


//    override fun onCheckedChanged(buttonView: CompoundButton?, isChecked: Boolean) {
//        if (binding.switchReminder.isChecked) {
//            context?.let { alarmReceiver.setRepeatingAlarm(it, "15.00") }
//
//        } else {
//            context?.let { alarmReceiver.cancelAlarm(it) }
//        }
//    }

    override fun onSharedPreferenceChanged(sharedPreferences: SharedPreferences?, key: String?) {
        Log.d("KEY", key!!)
        if (key == SET){
            binding.switchReminder.isChecked = sharedPreferences!!.getBoolean(SET, false)
            if (binding.switchReminder.isChecked){
                context?.let { alarmReceiver.setRepeatingAlarm(it, "15.00") }
            } else {
                context?.let { alarmReceiver.cancelAlarm(it) }
            }
        }


    }

    private fun isSwitchChecked() {
        binding.switchReminder.setOnCheckedChangeListener { _, _ ->
            if (binding.switchReminder.isChecked) {
                context?.let { alarmReceiver.setRepeatingAlarm(it, "15.00") }
            } else {
                context?.let {
                    alarmReceiver.cancelAlarm(it)
                }
            }
        }

    }
    private fun setSummaries() {
        val sh = preferenceManager.sharedPreferences
        binding.switchReminder.isChecked = sh.getBoolean(SET, false)

    }

    override fun onResume() {
        super.onResume()
        preferenceScreen.sharedPreferences.registerOnSharedPreferenceChangeListener(this)
    }
    override fun onPause() {
        super.onPause()
        preferenceScreen.sharedPreferences.unregisterOnSharedPreferenceChangeListener(this)
    }

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        alarmReceiver = AlarmReceiver()
        setSummaries()
    }
}