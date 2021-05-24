package com.example.stepcounter

import android.content.Intent
import android.content.pm.ResolveInfo
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.stepcounter.databinding.ActivityGoalsReachedBinding
import com.example.stepcounter.fragment.GoalsReachedFragment
import com.example.stepcounter.fragment.HomeFragment
import com.example.stepcounter.fragment.RunFragment
import java.io.UnsupportedEncodingException
import java.net.URLEncoder

class GoalsReachedActivity : AppCompatActivity() {
    private lateinit var binding: ActivityGoalsReachedBinding
    var targetReached = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGoalsReachedBinding.inflate(layoutInflater)

        val mFragmentManager = fragmentManager

        if(savedInstanceState != null){
            val target = savedInstanceState.getInt(ARG_TARGET)
            targetReached = target!!

            binding.tvStepsReached.text = targetReached.toString()
        }

        binding.btnShare.setOnClickListener {
            twitterAction()
        }

        binding.btnBackToHome.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        setContentView(binding.root)
    }

    fun twitterAction(){
        val tweetUrl = String.format(
                "https://twitter.com/intent/tweet?text=%s",
                urlEncode("Yay! I succeed to walk ${targetReached} steps today. Let's go out and walk with Step Counter!")
        )
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(tweetUrl))

        // Narrow down to official Twitter app, if available:
        val matches: List<ResolveInfo> = packageManager.queryIntentActivities(intent, 0)
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

    override fun onBackPressed() {
        super.onBackPressed()
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }


    companion object {
        var ARG_TARGET = "target"
    }
}