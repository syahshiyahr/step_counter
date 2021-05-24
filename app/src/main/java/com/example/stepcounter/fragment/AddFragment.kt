package com.example.stepcounter.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.stepcounter.R
import com.example.stepcounter.databinding.FragmentAddBinding
import com.example.stepcounter.databinding.FragmentHomeBinding


class AddFragment : Fragment() {
    private lateinit var binding: FragmentAddBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        binding = FragmentAddBinding.inflate(inflater, container, false)
        val mFragmentManager = fragmentManager

        binding.btnStartWalking.setOnClickListener {
            val mFragmentStart = StartFragment()

            mFragmentManager?.beginTransaction()?.apply {
                replace(R.id.frame_container, mFragmentStart, StartFragment::class.java.simpleName)
                addToBackStack(null)
                commit()
            }
        }

        binding.btnSetGoals.setOnClickListener {
            val mFragmentSetTarget = SetTargetFragment()

            mFragmentManager?.beginTransaction()?.apply {
                replace(R.id.frame_container, mFragmentSetTarget, SetTargetFragment::class.java.simpleName)
                addToBackStack(null)
                commit()
            }
        }

        binding.ivBack.setOnClickListener {
            requireActivity().onBackPressed()
        }

        return binding.root
    }


}