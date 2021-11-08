package com.ssafy.jobis.presentation.chat

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Canvas
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import com.ssafy.jobis.R
import com.ssafy.jobis.view.DrawingView
import com.waynejo.androidndkgif.GifEncoder
import java.io.File
import java.io.FileNotFoundException
import java.io.IOException

class DrawingFragment : Fragment() {

    private lateinit var canvas: DrawingView
    val bitmapList = ArrayList<Bitmap>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_drawing, container, false)

        canvas = view.findViewById(R.id.drawing_view)

        setHasOptionsMenu(true)
        return view
    }

    fun addView() {

        val bitmap = Bitmap.createBitmap(canvas.width, canvas.height, Bitmap.Config.ARGB_8888)
        val tmp = Canvas(bitmap)
        canvas.draw(tmp)

        bitmapList.add(bitmap)
        Log.d("비트맵 개수", bitmapList.size.toString())

//        val date = System.currentTimeMillis().toString()
//        val fileName = "$date.jpg"
//        try {
//
//            // getExternalFilesDir을 사용한다면 앱 이외에는 비공개이기 때문에 미디어 스캐너가 접근할 수 없습니다.
//            // getExternalStoragePublicDirectory을 통해 모든 앱이 공유하는 디렉토리를 가져옵니다. 미디어 스캐너가 접근할 수 있습니다.
//            val file = File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), fileName)
//
//            Log.d("ㅇㅇㅇ", file.absolutePath)
//
//            val outStream = FileOutputStream(file)
//            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outStream)
//
//            Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE).also {
//                it.data = Uri.fromFile(file)
//                context?.sendBroadcast(it)
//            }
//            outStream.close()
//
//        } catch (e: Exception) {
//            e.printStackTrace()
//        }
    }
    fun saveView() {
        Thread {
            try {
                encodeGIF()
                Log.d("스레드 중", "ㅋㅋㅋㅋㅋ")
            } catch (e: FileNotFoundException) {
                e.printStackTrace()
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }.start()
        Log.d("스레드 끝", "ㅋㅋㅋㅋㅋㅋ")
    }

    fun encodeGIF() {
        val fileName = System.currentTimeMillis().toString()+".gif"
        val file = File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), fileName)
//        val filePath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).absolutePath + File.separator + fileName
        val filePath = file.absolutePath
        val width = canvas.width
        val height = canvas.height
        val delayMs = 100;

        val gifEncoder = GifEncoder()
        gifEncoder.init(width, height, filePath, GifEncoder.EncodingType.ENCODING_TYPE_NORMAL_LOW_MEMORY)
        gifEncoder.setDither(false)
        for (bm in bitmapList) {
            gifEncoder.encodeFrame(bm, delayMs)
        }
        gifEncoder.close()

        Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE).also {
            it.data = Uri.fromFile(file)
            context?.sendBroadcast(it)
        }
    }
}