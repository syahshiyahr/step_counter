package com.example.stepcounter.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.stepcounter.R
import com.example.stepcounter.databinding.FragmentSetTargetBinding


class SetTargetFragment : Fragment() {
    private lateinit var binding: FragmentSetTargetBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentSetTargetBinding.inflate(inflater, container, false)
        val mFragmentManager = fragmentManager


        binding.plus.setOnClickListener {
            binding.etTarget.setText("${binding.etTarget.text.toString().toInt() + 1}")
        }

        binding.minus.setOnClickListener {
            binding.etTarget.setText("${binding.etTarget.text.toString().toInt() - 1}")
        }

        binding.btnStart.setOnClickListener {
            val mFragmentRun = RunFragment()

            val mBundle = Bundle()
            mBundle.putInt(RunFragment.ARG_TARGET, binding.etTarget.text.toString().toInt())
            mFragmentRun.arguments = mBundle

            mFragmentManager?.beginTransaction()?.apply {
                replace(R.id.frame_container, mFragmentRun, RunFragment::class.java.simpleName)
                addToBackStack(null)
                commit()
            }
        }

        binding.btnStop.setOnClickListener {

        }

        return binding.root
    }

}