package com.example.stepcounter.fragment

import android.content.SharedPreferences
import android.os.Bundle
import androidx.preference.CheckBoxPreference
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.SwitchPreference
import com.example.stepcounter.R

class MyPreferenceFragment : PreferenceFragmentCompat(),
    SharedPreferences.OnSharedPreferenceChangeListener {
    private lateinit var isSetReminder: SwitchPreference
    private lateinit var SET: String
    private lateinit var alarmReceiver: AlarmReceiver

    override fun onCreatePreferences(bundle: Bundle?, s: String?) {
        addPreferencesFromResource(R.xml.preferences)
        alarmReceiver = AlarmReceiver()
        init()
        setSummaries()

    }

    private fun init() {
        SET = "Set Reminder"
        isSetReminder = findPreference<SwitchPreference>(SET) as SwitchPreference
    }

    override fun onSharedPreferenceChanged(sharedPreferences: SharedPreferences, key: String) {
        if (key == SET) {
            isSetReminder.isChecked = sharedPreferences.getBoolean(SET, false)
            if (isSetReminder.isChecked) {
                context?.let {
//                    alarmReceiver.setRepeatingAlarm(
//                        it, AlarmReceiver.TYPE_REPEATING
//                    )
                    alarmReceiver.setOneTimeAlarm(context!!, AlarmReceiver.TYPE_ONE_TIME, "Don't forget to walk today. Let's go out and have a fresh breath.")
                }

            } else {
                context?.let { alarmReceiver.cancelAlarm(it, AlarmReceiver.TYPE_ONE_TIME) }
            }
        }
    }

    private fun setSummaries() {
        //untuk menyimpan nilai yang baru sesuai dengan data yang tersimpan.
        val sh = preferenceManager.sharedPreferences
        isSetReminder.isChecked = sh.getBoolean(SET, false)
    }

    override fun onResume() {
        super.onResume()
        preferenceScreen.sharedPreferences.registerOnSharedPreferenceChangeListener(this)
    }
    override fun onPause() {
        super.onPause()
        preferenceScreen.sharedPreferences.unregisterOnSharedPreferenceChangeListener(this)
    }


}