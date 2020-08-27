package com.ebookfrenzy.directreply

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.graphics.Color
import android.content.Intent
import android.app.RemoteInput
import android.view.View
import android.app.PendingIntent
import android.graphics.drawable.Icon
import android.app.Notification
import androidx.core.content.ContextCompat

import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val notificationId = 101
    private val KEY_TEXT_REPLY = "key_text_reply"

    private var notificationManager: NotificationManager? = null
    private val channelID = "com.ebookfrenzy.directreply.news"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        notificationManager =
                getSystemService(
                        Context.NOTIFICATION_SERVICE) as NotificationManager

        createNotificationChannel(channelID,
                "DirectReply News", "Example News Channel")

        handleIntent()
    }

    private fun handleIntent() {

        val intent = this.intent

        val remoteInput = RemoteInput.getResultsFromIntent(intent)

        if (remoteInput != null) {

            val inputString = remoteInput.getCharSequence(
                    KEY_TEXT_REPLY).toString()

            textView.text = inputString

            val repliedNotification = Notification.Builder(this, channelID)
                    .setSmallIcon(
                            android.R.drawable.ic_dialog_info)
                    .setContentText("Reply received")
                    .build()

            notificationManager?.notify(notificationId,
                    repliedNotification)

        }
    }

    fun sendNotification(view: View) {

        val replyLabel = "Enter your reply here"
        val remoteInput = RemoteInput.Builder(KEY_TEXT_REPLY)
                .setLabel(replyLabel)
                .build()
        val resultIntent = Intent(this, MainActivity::class.java)

        val resultPendingIntent = PendingIntent.getActivity(
                this,
                0,
                resultIntent,
                PendingIntent.FLAG_UPDATE_CURRENT
        )

        val icon = Icon.createWithResource(this@MainActivity,
                android.R.drawable.ic_dialog_info)

        val replyAction = Notification.Action.Builder(
                icon,
                "Reply", resultPendingIntent)
                .addRemoteInput(remoteInput)
                .build()

        val newMessageNotification = Notification.Builder(this, channelID)
                .setColor(ContextCompat.getColor(this,
                        R.color.colorPrimary))
                .setSmallIcon(
                        android.R.drawable.ic_dialog_info)
                .setContentTitle("My Notification")
                .setContentText("This is a test message")
                .addAction(replyAction).build()

        val notificationManager = getSystemService(
                Context.NOTIFICATION_SERVICE) as NotificationManager

        notificationManager.notify(notificationId,
                newMessageNotification)

    }

    private fun createNotificationChannel(id: String,
                                          name: String, description: String) {

        val importance = NotificationManager.IMPORTANCE_HIGH
        val channel = NotificationChannel(id, name, importance)

        channel.description = description
        channel.enableLights(true)
        channel.lightColor = Color.RED
        channel.enableVibration(true)
        channel.vibrationPattern =
                longArrayOf(100, 200, 300, 400, 500, 400, 300, 200, 400)

        notificationManager?.createNotificationChannel(channel)
    }



}