package com.example.workwithworkmanager

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import android.widget.Toast.LENGTH_LONG
import android.widget.Toast.LENGTH_SHORT
import androidx.work.Data
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkInfo
import androidx.work.WorkManager
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var request: OneTimeWorkRequest
    private lateinit var button: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        button = findViewById(R.id.button)
        onClick()
    }

    private fun onClick() {
        button.setOnClickListener {
            sendRequest(setRequestData(button.text.toString()))
            observerNotificationProcess()
        }
    }

    private fun setRequestData(title: String): Data {
        return Data.Builder()
            .putString("title", "$title clicked")
            .putString("desc", "this is test description")
            .build()
    }

    private fun sendRequest(data: Data) {
        request = OneTimeWorkRequest.Builder(NotificationWorker::class.java)
            .setInputData(data)
            .build()
        WorkManager.getInstance(this).enqueue(request)
    }

    /* get the last status of work
    * ENQUEUED
    * RUNNING
    * SUCCEEDED */
    private fun observerNotificationProcess() {
        WorkManager.getInstance(this).getWorkInfoByIdLiveData(request.id).observe(this, {
            if (it.state.name == "SUCCEEDED")
                Toast.makeText(this, it.state.name, LENGTH_SHORT).show()
        })
    }
}