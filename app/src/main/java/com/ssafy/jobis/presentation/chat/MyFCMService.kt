package com.ssafy.jobis.presentation.chat

import android.app.ActivityManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.ssafy.jobis.R
import java.lang.Exception


class MyFCMService: FirebaseMessagingService() {

    companion object {
        val CHANNEL_ID = "FCM"
        val CHANNEL_NAME = "MyChatFCM"
        val NOTIFICATION_ID = 12345678
    }

    private lateinit var mNotificationManager: NotificationManager

    override fun onCreate() {
        Log.d("파이어베이스 메시징 서비스", "onCreate")

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            val channelId = CHANNEL_ID
            val channelName = CHANNEL_NAME

            mNotificationManager = getSystemService(NotificationManager::class.java)

            mNotificationManager.createNotificationChannel(
                NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_LOW)
            )
        }
    }

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        Log.d("안녕하세요", "안녕하시려니까")

        var roomId = "디폴트"
        var userId = "디폴트"
        var content = "디폴트"

        if (remoteMessage.data.isNotEmpty()) {
            Log.d("값이 들어왔나요?", remoteMessage.data.toString())
            remoteMessage.let {
                roomId = it.data["roomId"].toString()
                userId = it.data["userId"].toString()
                content = it.data["content"].toString()
            }
        }

        val builder = NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentTitle("타이틀")
            .setContentText("${roomId}에서 ${userId}가 ${content}라고 말헀습니다.")
            .setSmallIcon(R.mipmap.ic_main)
            .setVibrate(longArrayOf(1000, 1000, 1000))

        mNotificationManager.notify(NOTIFICATION_ID, builder.build())
    }


}