package com.ssafy.jobis.presentation.chat

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import com.ssafy.jobis.data.model.study.Chat
import com.ssafy.jobis.data.model.study.StudyDatabase
import com.ssafy.jobis.data.model.study.StudyWithChats
import org.json.JSONObject
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.URL
import java.util.*

class ChatRepository(application: Application) {

    private val db = StudyDatabase.getInstance(application)
    private val dao = db?.getStudyDao()

    fun getChatList(studyId: String): LiveData<StudyWithChats>? {
        return dao?.getChatList(studyId)
    }

    fun saveMessage(studyId: String, userId: String, content: String, createdAt: String) {
        val chat = Chat(study_id = studyId, user_id = userId, content = content, created_at = createdAt)
        dao?.insertChat(chat)
    }

    fun uploadMessage(studyId: String, userId: String, content: String, createdAt: String) {

        val root = JSONObject()
        val data = JSONObject()
        data.apply {
            put("user_id", userId)
            put("studyId", studyId)
            put("content", content)
            put("created_at", createdAt)
        }
        root.put("data", data)
//        val notification = JSONObject()
//        notification.put("title", "반갑습니다")
//        notification.put("body", "안녕하세요ㅋㅋㅋ")
//        notification을 넣으면 자동으로 알림이 옴. 커스텀하고싶다면 서비스에서 재정의해야 됨
//        root.put("notification", notification)
        root.put("to", "/topics/${studyId}")


        val url = URL("https://fcm.googleapis.com/fcm/send")
        val httpConnect = url.openConnection() as HttpURLConnection
        httpConnect.apply {
            requestMethod = "POST"
            doOutput = true
            doInput = true
            addRequestProperty("Authorization", "key=AAAAyi-siU4:APA91bH-r-bKju0IKHUADgXlDlpIZLEo1blM7TaYHW_k_DWQZzwwRgxlLvqLdkMoTnSAOD1NXiDWf2tBb15eY3K4AqFXeY7HYk4n21rn1UqEweNOU6TXjMh9E7vQRJCDM1dj17CmAcIv")
            setRequestProperty("Accept", "application/json")
            setRequestProperty("Content-type", "application/json")
        }
        val os = httpConnect.outputStream
        os.write(root.toString().toByteArray(charset("utf-8")))
        os.flush()
        val responseCode = httpConnect.responseCode
        if (responseCode == 200) {
            Log.d("성공", inputStreamToString(httpConnect.inputStream))
        } else {
            Log.d("실패", "실패")
        }

    }

    private fun inputStreamToString(inputStream: InputStream): String {
        val stringBuilder = StringBuilder()
        val scanner = Scanner(inputStream)
        while (scanner.hasNext()) {
            stringBuilder.append(scanner.nextLine())
        }
        return stringBuilder.toString()
    }
}