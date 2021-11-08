package com.example.jobis.presentation.chat

import android.graphics.Bitmap
import android.graphics.Canvas
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import com.ssafy.jobis.R
import com.ssafy.jobis.view.DrawingView
import com.ssafy.jobis.view.DrawingView.Companion.curShapee
import com.ssafy.jobis.view.DrawingView.Companion.PAN
import com.ssafy.jobis.view.DrawingView.Companion.LINE
import com.ssafy.jobis.view.DrawingView.Companion.CIRCLE
import com.ssafy.jobis.view.DrawingView.Companion.SQ
import com.ssafy.jobis.view.DrawingView.Companion.color
import com.ssafy.jobis.view.DrawingView.Companion.size
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

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        menu?.add(0, 0, 0, "펜")
        menu?.add(0, 1, 0, "선 그리기")
        menu?.add(0, 2, 0, "원 그리기")
        menu?.add(0, 3, 0, "사각형 그리기")

        val sMenu = menu?.addSubMenu("색상변경==>")
        sMenu?.add(0, 4, 0, "빨강색")
        sMenu?.add(0, 5, 0, "파랑색")
        sMenu?.add(0, 6, 0, "초록색")
        sMenu?.add(0, 7, 0, "선 굵게")
        sMenu?.add(0, 8, 0, "선 가늘게")
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item?.itemId) {
            0 -> {
                curShapee = PAN
                return true
            }
            1 -> {
                curShapee = LINE
                return true
            }
            2 -> {
                curShapee = CIRCLE
                return true
            }
            3 -> {
                curShapee = SQ
                return true
            }
            4 -> {
                color = 1
                return true
            }
            5 -> {
                color = 2
                return true
            }
            6 -> {
                color = 3
                return true
            }
            7 -> {
                size += 5
                return true
            }
            8 -> {
                size -= 5
                return true
            }
        }
        return super.onOptionsItemSelected(item)
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
        val filePath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).absolutePath + File.separator + fileName
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
    }
}