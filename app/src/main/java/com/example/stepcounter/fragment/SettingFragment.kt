package com.example.stepcounter.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CompoundButton
import androidx.fragment.app.Fragment
import com.example.stepcounter.databinding.FragmentSettingBinding


class SettingFragment : Fragment(), CompoundButton.OnCheckedChangeListener {
    private lateinit var binding: FragmentSettingBinding
    private lateinit var alarmReceiver: AlarmReceiver

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentSettingBinding.inflate(inflater, container, false)
        alarmReceiver = AlarmReceiver()

        return binding.root
    }

    override fun onCheckedChanged(buttonView: CompoundButton?, isChecked: Boolean) {
        if (binding.switchReminder.isChecked) {
            context?.let { alarmReceiver.setRepeatingAlarm(it, "15.00") }
        } else {
            context?.let { alarmReceiver.cancelAlarm(it) }
        }
    }
}