package com.example.stepcounter.fragment

import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.os.Build
import android.provider.Settings.System.DATE_FORMAT
import android.util.Log
import android.widget.Toast
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import com.example.stepcounter.MainActivity
import com.example.stepcounter.R
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

class AlarmReceiver : BroadcastReceiver() {

    companion object {
        const val TYPE_ONE_TIME = "OneTimeAlarm"
        const val TITLE = "Step Counter"

        const val EXTRA_MESSAGE = "message"
        const val EXTRA_TYPE = "type"

        var isSet = false

        private const val ID_ONETIME = 100

        private const val ID_REPEATING = 101
        private const val TIME_FORMAT = "HH:mm"
    }

    override fun onReceive(context: Context, intent: Intent) {
        val message = intent.getStringExtra(EXTRA_MESSAGE)
        val title = TITLE
        val notifId = ID_ONETIME
        showAlarmNotification(context, title, message, notifId)

    }
    fun setOneTimeAlarm(context: Context, type: String, message: String) {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(context, AlarmReceiver::class.java)
        intent.putExtra(EXTRA_MESSAGE, message)
        intent.putExtra(EXTRA_TYPE, type)
        val pendingIntent = PendingIntent.getBroadcast(context, ID_ONETIME, intent, 0)
        alarmManager.set(AlarmManager.RTC_WAKEUP, 0, pendingIntent)
        Toast.makeText(context, "Reminder set up", Toast.LENGTH_SHORT).show()
    }

//    fun setRepeatingAlarm(context: Context, type: String) {
//        //val time = "22:07"
//        //if (isDateInvalid(time, TIME_FORMAT)) return
//        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
//        val intent = Intent(context, AlarmReceiver::class.java)
//        intent.addFlags(Intent.FLAG_RECEIVER_FOREGROUND)
//        intent.putExtra(EXTRA_MESSAGE, "Don't forget to walk today. Let's go out and have a fresh breath.")
//        //val timeArray = time.split(":".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
//        val calendar = Calendar.getInstance()
//        calendar.set(Calendar.HOUR_OF_DAY, 1)
//        calendar.set(Calendar.MINUTE, 6)
//        calendar.set(Calendar.SECOND, 0)
//        val pendingIntent = PendingIntent.getBroadcast(context, ID_REPEATING, intent, 0)
//        alarmManager.setInexactRepeating(
//            AlarmManager.RTC_WAKEUP,
//            calendar.timeInMillis,
//            AlarmManager.INTERVAL_DAY,
//            pendingIntent
//        )
//        Toast.makeText(context, "Set Reminder", Toast.LENGTH_SHORT).show()
//        isSet = true
//    }

//    private fun isDateInvalid(date: String, format: String): Boolean {
//        return try {
//            val df = SimpleDateFormat(format, Locale.getDefault())
//            df.isLenient = false
//            df.parse(date)
//            false
//        } catch (e: ParseException) {
//            true
//        }
//    }

    fun cancelAlarm(context: Context, type: String) {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(context, AlarmReceiver::class.java)
        val requestCode = ID_ONETIME
        val pendingIntent = PendingIntent.getBroadcast(context, requestCode, intent, 0)
        pendingIntent.cancel()
        alarmManager.cancel(pendingIntent)
        Toast.makeText(context, "Cancel", Toast.LENGTH_SHORT)
            .show()
        isSet = false
    }

    fun showAlarmNotification(
        context: Context,
        title: String,
        message: String?,
        notifId: Int
    ) {
        val intent = Intent(context, MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(context, 0, intent, 0)

        val CHANNEL_ID = "Channel_1"
        val CHANNEL_NAME = "AlarmManager channel"
        val notificationManagerCompat = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        val builder = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_baseline_directions_run_24)
            .setContentIntent(pendingIntent)
            .setContentTitle(title)
            .setContentText(message)
            .setColor(ContextCompat.getColor(context, android.R.color.transparent))
            .setVibrate(longArrayOf(1000, 1000, 1000, 1000, 1000))
            .setSound(alarmSound)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID,
                CHANNEL_NAME,
                NotificationManager.IMPORTANCE_DEFAULT
            )
            channel.enableVibration(true)
            channel.vibrationPattern = longArrayOf(1000, 1000, 1000, 1000, 1000)
            builder.setChannelId(CHANNEL_ID)
            notificationManagerCompat.createNotificationChannel(channel)
        }
        val notification = builder.build()
        notificationManagerCompat.notify(notifId, notification)
    }

}