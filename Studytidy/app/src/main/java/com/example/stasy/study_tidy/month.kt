package com.example.stasy.study_tidy

import android.annotation.SuppressLint
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




class month : AppCompatActivity() {

    private var gestureDetectorCompat: GestureDetectorCompat? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_month)
       var mcv = findViewById(R.id.calendarView) as MaterialCalendarView
        mcv.setPagingEnabled(false)
        var t = 10
        mcv.setOnDateChangedListener(OnDateSelectedListener { widget, date, selected -> val dayActivity = Intent(applicationContext, day::class.java)
            dayActivity.putExtra("Date", t)
            startActivity(dayActivity); })

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
