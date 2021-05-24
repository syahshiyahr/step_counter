package com.example.stepcounter.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.stepcounter.R
import com.example.stepcounter.adapter.HistoryAdapter
import com.example.stepcounter.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {
    private lateinit var adapter: HistoryAdapter
    private lateinit var binding: FragmentHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(inflater, container, false)

        binding.btnRun.setOnClickListener {
            val mFragmentManager = fragmentManager
            val mFragmentAdd = AddFragment()

            mFragmentManager?.beginTransaction()?.apply {
                replace(R.id.frame_container, mFragmentAdd, AddFragment::class.java.simpleName)
                addToBackStack(null)
                commit()
            }
        }

        return binding.root
    }
}