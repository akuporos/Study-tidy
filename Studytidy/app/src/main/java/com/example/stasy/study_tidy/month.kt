package com.example.stasy.study_tidy

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.view.GestureDetectorCompat
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import android.widget.CalendarView
import com.prolificinteractive.materialcalendarview.MaterialCalendarView
import com.prolificinteractive.materialcalendarview.CalendarDay
import android.support.annotation.NonNull
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener
import com.prolificinteractive.materialcalendarview.OnRangeSelectedListener
import kotlinx.android.synthetic.main.activity_month.*


class month : Activity() {

    private var gestureDetectorCompat: GestureDetectorCompat? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_month)
        var mcv = findViewById(R.id.calendarView) as MaterialCalendarView
        var dayActivity = Intent(applicationContext, day::class.java)

        //TODO: отображать сессионную неделю

        val monthList : List<String> = listOf("января", "февраля", "марта", "апреля", "мая", "июня", "июля",
        "августа", "сентября", "октября", "ноября", "декабря")

        mcv.setSelectionMode(MaterialCalendarView.SELECTION_MODE_RANGE)

        mcv.setOnDateChangedListener(OnDateSelectedListener { widget, date, selected ->
            mcv.setOnLongClickListener(object : View.OnLongClickListener {
                override fun onLongClick(v: View): Boolean {
                    return true
                }
            })
            var dateToSend= date.day.toString() + " " + monthList[date.month]
            dayActivity.putExtra("Date", dateToSend)
            startActivity(dayActivity)
        })
        mcv.setOnRangeSelectedListener(OnRangeSelectedListener{widget, dates ->
            button.setOnClickListener (object : View.OnClickListener {
                override fun onClick(v: View) {
                    for(day in dates )
                    {
                        var dateToAdd = day.date.toString() + " " + day.month.toString()
                        DateStorage.addEvent(dateToAdd, DateStorage.educationEvent, "Session")
                    }
                }
            })
        })
        gestureDetectorCompat = GestureDetectorCompat(this, MyGestureListener())
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        this.gestureDetectorCompat!!.onTouchEvent(event)
        return super.onTouchEvent(event)
    }

    internal inner class MyGestureListener : GestureDetector.SimpleOnGestureListener() {
        //handle 'swipe right' action only
        override fun onFling(event1: MotionEvent, event2: MotionEvent,
                             velocityX: Float, velocityY: Float): Boolean {
            if (event2.x > event1.x) {
                finish()
            }
            return true
        }
    }
}

private fun MaterialCalendarView.setOnLongClickListener(mcv: MaterialCalendarView) {

}
