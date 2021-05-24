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
                    alarmReceiver.setRepeatingAlarm(
                        it, AlarmReceiver.TYPE_REPEATING,
                        "09:00", "Let's find popular user on Github!"
                    )
                }
            } else {
                context?.let { alarmReceiver.cancelAlarm(it, AlarmReceiver.TYPE_REPEATING) }
            }
        }
    }

    private fun setSummaries() {
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