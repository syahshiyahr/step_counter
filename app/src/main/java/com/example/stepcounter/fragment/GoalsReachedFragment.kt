package com.example.stepcounter.fragment

import android.content.Intent
import android.content.pm.ResolveInfo
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.stepcounter.R
import com.example.stepcounter.databinding.FragmentGoalsReachedBinding
import java.io.UnsupportedEncodingException
import java.net.URLEncoder


class GoalsReachedFragment : Fragment() {
    private lateinit var binding: FragmentGoalsReachedBinding
    var targetReached = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentGoalsReachedBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        if(arguments != null){
            val target = arguments?.getInt(RunFragment.ARG_TARGET)
            targetReached = target!!

            binding.tvStepsReached.text = targetReached.toString()
        }

        binding.btnShare.setOnClickListener {
            twitterAction()
        }

        binding.btnBackToHome.setOnClickListener {
            val mFragmentManager = fragmentManager
            val mFragmentHome = HomeFragment()

            mFragmentManager?.beginTransaction()?.apply {
                replace(R.id.frame_container, mFragmentHome, HomeFragment::class.java.simpleName)
                addToBackStack(null)
                commit()
            }
        }

    }

    fun twitterAction(){
        val tweetUrl = String.format(
            "https://twitter.com/intent/tweet?text=%s",
            urlEncode("Yay! I succeed to walk ${targetReached} steps today. Let's go out and walk with Step Counter!")
        )
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(tweetUrl))

        // Narrow down to official Twitter app, if available:
        val matches: List<ResolveInfo> = activity!!.packageManager.queryIntentActivities(intent, 0)
        for (info in matches) {
            if (info.activityInfo.packageName.toLowerCase().startsWith("com.twitter")) {
                intent.setPackage(info.activityInfo.packageName)
            }
        }

        startActivity(intent)
    }

    private fun urlEncode(s: String): Any? {
        return try {
            URLEncoder.encode(s, "UTF-8")
        } catch (e: UnsupportedEncodingException) {
            throw RuntimeException("URLEncoder.encode() failed for $s")
        }

    }




    companion object {
        var ARG_TARGET = "target"
    }
}