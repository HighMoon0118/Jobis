package com.example.materialcalendar

import android.content.Context
import android.graphics.Color
import android.graphics.Typeface
import android.text.style.ForegroundColorSpan
import android.text.style.RelativeSizeSpan
import android.text.style.StyleSpan
import com.prolificinteractive.materialcalendarview.CalendarDay
import com.prolificinteractive.materialcalendarview.DayViewDecorator
import com.prolificinteractive.materialcalendarview.DayViewFacade
import java.util.*

// 오늘 날짜에 테두리 넣어주기
class OneDayDecorator() : DayViewDecorator {

    var date : CalendarDay = CalendarDay.today()
    override fun shouldDecorate(day: CalendarDay?): Boolean {
        return date != null && date == day
    }

    override fun decorate(view: DayViewFacade?) {
        view?.addSpan(StyleSpan(Typeface.BOLD))
        view?.addSpan(ForegroundColorSpan(Color.parseColor("#ff4e7e")))
        // 테두리??
    }

    public fun setDate(date: Date) {
        this.date = CalendarDay.from(date)
    }
}