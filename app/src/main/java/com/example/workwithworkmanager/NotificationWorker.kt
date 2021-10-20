package com.example.workwithworkmanager

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.graphics.BitmapFactory
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.work.Data
import androidx.work.Worker
import androidx.work.WorkerParameters

class NotificationWorker(private val context: Context, private val params: WorkerParameters) :
    Worker(context, params) {


    override fun doWork(): Result {
        val data: Data = inputData
        displayNotification(data.getString("title").toString(), data.getString("desc").toString())
        return Result.success()
    }

    private fun displayNotification(task: String, desc: String) {
        val notificationManager: NotificationManager =
            applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel =
                NotificationChannel("notification", "test", NotificationManager.IMPORTANCE_DEFAULT)
            notificationManager.createNotificationChannel(channel)
        }

        val notificationBuilder: NotificationCompat.Builder =
            NotificationCompat.Builder(applicationContext, "notification")
                .setContentTitle(task)
                .setContentText(desc)
                .setSmallIcon(R.mipmap.ic_launcher)

        notificationManager.notify(1, notificationBuilder.build())
    }

}