package com.example.stepcounter

import android.content.ContentValues
import android.content.Intent
import android.content.pm.ResolveInfo
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.stepcounter.databinding.ActivityGoalsReachedBinding
import com.example.stepcounter.db.DatabaseContract.NoteColumns.Companion.DATE
import com.example.stepcounter.db.DatabaseContract.NoteColumns.Companion.TARGET
import com.example.stepcounter.db.DatabaseContract.NoteColumns.Companion.TIME
import com.example.stepcounter.db.StoryHelper
import com.example.stepcounter.entity.History
import java.io.UnsupportedEncodingException
import java.net.URLEncoder
import java.text.SimpleDateFormat
import java.util.*

class GoalsReachedActivity : AppCompatActivity() {
    private lateinit var binding: ActivityGoalsReachedBinding
    var targetReached = 0
    private lateinit var helper: StoryHelper
    private var history: History? = null

    private var position: Int = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGoalsReachedBinding.inflate(layoutInflater)
        val target = intent.getIntExtra(ARG_TARGET, 0)
        targetReached = target!!
        Log.d("Target Goals ", targetReached.toString())

        binding.tvStepsReached.text = targetReached.toString()
        binding.btnShare.setOnClickListener {
            twitterAction()
        }

        //add to history
        helper = StoryHelper.getInstance(applicationContext)
        helper.open()

        history = History()

        binding.btnBackToHome.setOnClickListener {
            saveHistory()
        }

        setContentView(binding.root)
    }

    private fun saveHistory() {
        history?.date = getCurrentDate()
        history?.time = getCurrentTime()
        history?.target = targetReached

        val intent = Intent(this, MainActivity::class.java)
        intent.putExtra(EXTRA_HISTORY, history)
        intent.putExtra(EXTRA_POSITION, position)

        val values = ContentValues()
        values.put(TARGET, targetReached)
        values.put(DATE, getCurrentDate())
        values.put(TIME, getCurrentTime())
        val result = helper.insert(values)

//            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)

        if(result > 0){
            history?.id = result.toInt()
            setResult(RESULT_ADD, intent)
            finish()
        }

        Toast.makeText(this, "Satu item berhasil disimpan", Toast.LENGTH_SHORT).show()
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
        startActivity(intent)
        finish()
    }

    private fun getCurrentTime(): String {
        val dateFormat = SimpleDateFormat("HH:mm", Locale.getDefault())
        val date = Date()

        return dateFormat.format(date)
    }

    private fun getCurrentDate(): String {
        val dateFormat = SimpleDateFormat("yyyy/MM/dd", Locale.getDefault())
        val date = Date()

        return dateFormat.format(date)
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
        saveHistory()
    }

    companion object {
        var ARG_TARGET = "target"
        const val EXTRA_HISTORY = "extra_history"
        const val EXTRA_POSITION = "extra_position"
        const val REQUEST_ADD = 100
        const val RESULT_ADD = 101
        const val REQUEST_UPDATE = 200
        const val RESULT_UPDATE = 201
        const val RESULT_DELETE = 301
        const val ALERT_DIALOG_CLOSE = 10
        const val ALERT_DIALOG_DELETE = 20
    }
}