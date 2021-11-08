package com.example.jobis.view

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View

class DrawingView(context: Context, attrs: AttributeSet): View(context, attrs) {
    companion object {
        val PAN = 0
        val LINE = 1 // 선
        val CIRCLE = 2 // 원
        val SQ = 3 // 사각형
        var curShapee = PAN // curShape = 1
        var color = 1 // 색상 빨강 파랑 녹색
        var size = 5 // 선 굵기 기본값
    }

    var sX = -1
    var sY = -1
    var eX = -1
    var eY = -1
    private val path: Path = Path()

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        when (event?.action) {
            MotionEvent.ACTION_DOWN -> {
                path.reset()
                sX = event.x.toInt()
                sY = event.y.toInt()
                path.moveTo(sX.toFloat(), sY.toFloat())
            }
            MotionEvent.ACTION_MOVE, MotionEvent.ACTION_UP -> {
                eX = event.x.toInt()
                eY = event.y.toInt()
                path.lineTo(eX.toFloat(), eY.toFloat())
                this.invalidate()
            }
        }
        return true
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        val paint = Paint() // paint라는 객체를 생성하고
        paint.style = Paint.Style.STROKE // 채워지지 않는 도형 형성
        paint.strokeWidth = size.toFloat()

        if (color == 1) {
            paint.color = Color.RED
        } else if (color == 2) {
            paint.color = Color.BLUE
        } else {
            paint.color = Color.GREEN
        }

        when (curShapee) {
            PAN -> canvas?.drawPath(path, paint)

            LINE ->
                canvas?.drawLine(sX.toFloat(), sY.toFloat(), eX.toFloat(), eY.toFloat(), paint)

            // 피타고라스 정리 이용, 원의 반지름 구하기.
            CIRCLE -> {
                val radius = Math.sqrt(
                    Math.pow(
                        (eX - sX).toDouble(),
                        2.0) + Math.pow((eY - sY).toDouble(), 2.0)
                )
                // 결과적으로 2좌표의 거리를 산출.

                canvas?.drawCircle(sX.toFloat(), sY.toFloat(), radius.toFloat(), paint)


            }

            SQ -> {
                canvas?.drawRect(sX.toFloat(), sY.toFloat(), eX.toFloat(), eY.toFloat(), paint)
            }
        }
    }
}