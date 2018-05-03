package com.hseanastasia.stasy.study_tidy

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.view.GestureDetectorCompat
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import com.prolificinteractive.materialcalendarview.MaterialCalendarView
import com.prolificinteractive.materialcalendarview.CalendarDay
import android.util.Log
import com.google.gson.Gson
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener
import com.prolificinteractive.materialcalendarview.OnRangeSelectedListener
import kotlinx.android.synthetic.main.activity_month.*
import java.io.IOException
import java.io.OutputStreamWriter
import com.prolificinteractive.materialcalendarview.format.TitleFormatter
import java.text.SimpleDateFormat
import java.util.*


class month : Activity() {

    private var gestureDetectorCompat: GestureDetectorCompat? = null
    private val monthList : List<String> = listOf("января", "февраля", "марта", "апреля", "мая", "июня", "июля",
            "августа", "сентября", "октября", "ноября", "декабря")
    private val  lastDay : HashMap<Int, Int> = HashMap()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_month)
        var mcv = findViewById(R.id.calendarView) as MaterialCalendarView
        var dayActivity = Intent(applicationContext, day::class.java)

        val extras = intent.extras

        val month = extras.getString("month")
        val year = extras.getString("year").toInt()


        lastDay.set(1, 31)
        if(year % 4 == 0) {
            lastDay.set(2, 29)
        }
        else
        {
            lastDay.set(2, 28)
        }
        lastDay.set(3, 31)
        lastDay.set(4, 30)
        lastDay.set(5, 31)
        lastDay.set(6, 30)
        lastDay.set(7, 31)
        lastDay.set(8, 31)
        lastDay.set(9, 30)
        lastDay.set(10, 31)
        lastDay.set(11, 30)
        lastDay.set(12, 31)


        val firstDate = Calendar.getInstance()
        firstDate.set(year, month.toInt(), 1, 0, 0, 0)  // 1st February, 2016
        val lastDate = Calendar.getInstance()
        lastDate.set(year, month.toInt(), lastDay.get(month.toInt()+1)!!, 0, 0, 0)  // 29th February, 2016

        mcv.state().edit()
                .setMinimumDate(firstDate) // 1st February, 2016
                .setMaximumDate(lastDate) // 29th February, 2016
                .commit()

        mcv.setTitleFormatter(TitleFormatter {
            val simpleDateFormat = SimpleDateFormat("MMMM yyyy", Locale.ENGLISH) //"February 2016" format

            val monthAndYear = simpleDateFormat.format(firstDate.getTime())

            simpleDateFormat.format(firstDate.getTime())
        })


        mcv.setSelectionMode(MaterialCalendarView.SELECTION_MODE_RANGE)

        mcv.setOnDateChangedListener(OnDateSelectedListener { widget, date, selected ->
            var dateToSend= date.day.toString() + " " + monthList[date.month] + " " + date.year
            button1.setOnClickListener (object : View.OnClickListener {
                override fun onClick(v: View) {
                    dayActivity.putExtra("Date", dateToSend)
                    startActivity(dayActivity)
                 }
            })
        })

        mcv.setOnRangeSelectedListener(OnRangeSelectedListener{widget, dates ->
            button.setOnClickListener (object : View.OnClickListener {
                override fun onClick(v: View) {
                    for(day in dates )
                    {
                        var dateToAdd = day.day.toString() + " " + (day.month + 1).toString() + " " + day.year.toString()
                        DateStorage.addEvent(dateToAdd, DateStorage.educationEvent, "Session")
                    }
                }
            })
        })
        gestureDetectorCompat = GestureDetectorCompat(this, MyGestureListener())
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
}
