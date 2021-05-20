package com.example.stepcounter.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.stepcounter.R
import com.example.stepcounter.databinding.FragmentRunBinding

class RunFragment : Fragment() {
    private lateinit var binding: FragmentRunBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        binding = FragmentRunBinding.inflate(inflater, container, false)

        return binding.root
    }

}