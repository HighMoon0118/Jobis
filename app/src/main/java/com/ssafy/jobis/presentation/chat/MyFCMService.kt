package com.ssafy.jobis.presentation.chat

import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.ssafy.jobis.R


class MyFCMService: FirebaseMessagingService() {

    companion object {
        val CHANNEL_ID = "FCM"
        val CHANNEL_NAME = "MyChatFCM"
        val NOTIFICATION_ID = 12345678

        var currentStudyId = ""
    }
    private lateinit var mNotificationManager: NotificationManager
    private lateinit var repo: ChatRepository  // oncreate밖에서 context를 가져오면 null 오류 발생

    override fun onCreate() {
        Log.d("파이어베이스 메시징 서비스", "onCreate")
        repo = ChatRepository(this)

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

        var studyId = ""
        var isMe = false
        var userId = ""
        var nickname = ""
        var content = ""
        var fileName = ""
        var createdAt = ""

        if (remoteMessage.data.isNotEmpty()) {
            Log.d("값이 들어왔나요?", remoteMessage.data.toString())
            remoteMessage.let {
                studyId = it.data["study_id"].toString()
                userId = it.data["user_id"].toString()
                isMe = it.data["is_me"].toBoolean()
                nickname = it.data["nickname"].toString()
                content = it.data["content"].toString()
                fileName = it.data["file_name"].toString()
                createdAt = it.data["created_at"].toString()
            }
            if (FirebaseAuth.getInstance().currentUser?.uid ?: "" != userId) {
                repo.saveMessage(studyId, userId, isMe, nickname, content, fileName, createdAt)
            }

        }



        if (FirebaseAuth.getInstance().currentUser?.uid ?: "" != userId && currentStudyId == "") {
            val builder = NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentTitle("메세지가 도착했습니다.")
                .setContentText(content)
                .setSmallIcon(R.mipmap.ic_main)
                .setVibrate(longArrayOf(1000, 0, 0))

            mNotificationManager.notify(NOTIFICATION_ID, builder.build())
        }
    }


}