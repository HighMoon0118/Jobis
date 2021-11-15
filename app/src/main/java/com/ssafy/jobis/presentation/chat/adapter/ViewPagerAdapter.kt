package com.ssafy.jobis.presentation.chat.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Environment
import android.util.Log
import android.widget.Toast
import androidx.fragment.app.FragmentActivity
import androidx.preference.PreferenceManager
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.ssafy.jobis.presentation.chat.DrawingFragment
import com.waynejo.androidndkgif.GifEncoder
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File

class ViewPagerAdapter(private val fragmentActivity: FragmentActivity) : FragmentStateAdapter(fragmentActivity){

    interface CanvasListener {
        fun onSuccess()
    }

    private val canvasList:Array<DrawingFragment> by lazy {
        Array(24) { DrawingFragment() }
    }
    private val used:Array<Boolean> by lazy {
        Array(24) { false }
    }
    val bitmapList = ArrayList<Bitmap>()

    override fun getItemCount(): Int {
        return 24
    }

    override fun createFragment(position: Int): DrawingFragment {
        return canvasList[position]
    }

    fun isAdded(position: Int): Boolean {
        return used[position]
    }

    fun addView(position: Int) {
        used[position] = true
    }
    fun removeView(position: Int) {
        used[position] = false
    }

    @SuppressLint("ShowToast", "CommitPrefEdits")
    fun saveView() {
        bitmapList.clear()

        for (i in used.indices) {
            if (used[i]) {
                bitmapList.add(canvasList[i].getBitmap())
            }
        }

        if (bitmapList.size == 0) {

        } else if (bitmapList.size == 1) {

        } else {
            val fileName = System.currentTimeMillis().toString()+".gif"
            val file = File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), fileName)

            val filePath = file.absolutePath
            val delayMs = 100;

            val wh = canvasList[0].getWH()

            CoroutineScope(Dispatchers.IO).launch {
                try {
                    Log.d("인코딩 시작", "ㅇㅇㅇㅇㅇ")
                    encodeGIF(wh[0], wh[1], filePath, delayMs)
                } catch (e: Exception) {
                    e.printStackTrace()
                } finally {
                    Log.d("인코딩 끝", "ㅇㅇㅇㅇㅇ")
                    val pref = PreferenceManager.getDefaultSharedPreferences(fragmentActivity)
                    val files = pref.getString("gif", "").toString() + filePath + "#"
                    pref.edit().apply {
                        putString("gif", files)
                        apply()
                    }

                    withContext(Dispatchers.Main) {
                        Toast.makeText(fragmentActivity, "GIF파일 생성!", Toast.LENGTH_SHORT).show()
                        if (fragmentActivity is CanvasListener) {
                            fragmentActivity.onSuccess()
                        }
                    }
                    Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE).also {
                        it.data = Uri.fromFile(file)
                        fragmentActivity.sendBroadcast(it)
                    }

                }
            }
        }
    }

    private fun encodeGIF(w: Int, h: Int, filePath: String, delayMs: Int) {

        val gifEncoder = GifEncoder()
        gifEncoder.init(w, h, filePath, GifEncoder.EncodingType.ENCODING_TYPE_NORMAL_LOW_MEMORY)
        gifEncoder.setDither(false)
        for (bm in bitmapList) {
            gifEncoder.encodeFrame(bm, delayMs)
        }
        gifEncoder.close()
    }
}