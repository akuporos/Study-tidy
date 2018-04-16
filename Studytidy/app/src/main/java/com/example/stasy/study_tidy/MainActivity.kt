package com.example.stasy.study_tidy

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import android.content.Intent
import android.app.Activity
import com.example.stasy.study_tidy.R.id.imageButton
import android.support.v4.view.GestureDetectorCompat
import android.view.MotionEvent
import android.widget.Toast
import android.view.GestureDetector
import android.text.method.Touch.onTouchEvent
import com.example.stasy.study_tidy.R.layout.activity_calendar


class MainActivity : AppCompatActivity() {

    private var gestureDetectorCompat: GestureDetectorCompat? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        var image_button = findViewById(R.id.imageButton) as ImageButton
        image_button.setOnClickListener(object : View.OnClickListener {
           override fun onClick(v: View) {
               val scrollingActivity = Intent(applicationContext, today::class.java)
               startActivity(scrollingActivity);
            }
        })
        gestureDetectorCompat = GestureDetectorCompat(this, MyGestureListener())
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        this.gestureDetectorCompat!!.onTouchEvent(event)
        return super.onTouchEvent(event)
    }
    internal inner class MyGestureListener : GestureDetector.SimpleOnGestureListener() {
        //handle 'swipe left' action only

        override fun onFling(event1: MotionEvent, event2: MotionEvent,
                             velocityX: Float, velocityY: Float): Boolean {

            if (event2.x < event1.x) {
                //switch another activity
                val intent = Intent(
                        this@MainActivity, calendar::class.java)
                startActivity(intent)
            }
            return true
        }
    }
}
