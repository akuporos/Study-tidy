package com.hseanastasia.stasy.study_tidy

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.view.GestureDetectorCompat
import android.widget.Toast
import android.view.MotionEvent
import android.view.GestureDetector
import android.text.method.Touch.onTouchEvent
import android.util.Log
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toolbar
import com.github.ik024.calendar_lib.custom.MonthView
import com.github.ik024.calendar_lib.custom.YearView
import com.github.ik024.calendar_lib.listeners.YearViewClickListeners
import com.google.gson.Gson
import java.io.IOException
import java.io.OutputStreamWriter
import java.util.*


class calendar : Activity(), YearViewClickListeners {

    internal lateinit var mYearView: YearView
    private var gestureDetectorCompat: GestureDetectorCompat? = null
    private//generating dummy event list
    val eventList: List<Date>
        get() {
            val eventList = ArrayList<Date>()
            eventList.add(getDate(2017, 4, 9))
            eventList.add(getDate(2016, 4, 11))
            eventList.add(getDate(2016, 4, 13))
            eventList.add(getDate(2016, 4, 15))

            return eventList
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_calendar)
        gestureDetectorCompat = GestureDetectorCompat(this, MyGestureListener())
        //val toolbar = findViewById(R.id.toolbar) as Toolbar
        //setSupportActionBar(toolbar)

        mYearView = findViewById(R.id.calendar_year_view) as YearView
        mYearView.registerYearViewClickListener(this)
        //adding events to the calendar
        //mYearView.setEventList(eventList)

        findMonthViews(mYearView)
    }

    private fun findMonthViews(view : ViewGroup) {
        val monthNameField = MonthView::class.java.getDeclaredField("mTvMonthName")
        monthNameField.isAccessible = true
        val selectedYear = MonthView::class.java.getDeclaredField("selectedYear")
        selectedYear.isAccessible = true
        val selectedMonth = MonthView::class.java.getDeclaredField("selectedMonth")
        selectedMonth.isAccessible = true

        for (i in 0 until view.childCount) {
            val ch = view.getChildAt(i)
            if (ch is ViewGroup)
                findMonthViews(ch)
            if (ch is MonthView) {
                val monthName = monthNameField.get(ch) as TextView
                monthName.setOnClickListener {
                    dateClicked(selectedYear.getInt(ch), selectedMonth.getInt(ch), -1)
                }
            }
        }
    }
    override fun onDestroy() {
        super.onDestroy()
        var gson = Gson()
        val json = gson.toJson(DateStorage.dataStorage)
        try {
            val outputStreamWriter = OutputStreamWriter(applicationContext.openFileOutput(DateStorage.filename, Context.MODE_PRIVATE))
            outputStreamWriter.write(json)
            outputStreamWriter.close()
        } catch (e: IOException) {
            Log.e("Exception", "File write failed: " + e.toString())
        }
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

    private fun getDate(year: Int, month: Int, day: Int): Date {
        val cal = Calendar.getInstance()
        cal.set(Calendar.YEAR, year)
        cal.set(Calendar.MONTH, month)
        cal.set(Calendar.DAY_OF_MONTH, day)
        cal.set(Calendar.HOUR_OF_DAY, 0)
        cal.set(Calendar.MINUTE, 0)
        cal.set(Calendar.SECOND, 0)
        cal.set(Calendar.MILLISECOND, 0)
        return cal.getTime()
    }

    override fun dateClicked(year: Int, _month: Int, _day: Int) {
        if (_day == -1)
        {
            val monthActivity = Intent(applicationContext, month::class.java)
            monthActivity.putExtra("year", year.toString())
            monthActivity.putExtra("month", _month.toString())
            startActivity(monthActivity)
        }
        else
        {
            val dayActivity = Intent(applicationContext, day::class.java)
            dayActivity.putExtra("day_year", year.toString())
            dayActivity.putExtra("day_month", _month.toString())
            dayActivity.putExtra("day", _day.toString())
            startActivity(dayActivity)
        }
    }
}


