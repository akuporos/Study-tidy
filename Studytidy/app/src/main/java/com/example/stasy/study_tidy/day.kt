package com.example.stasy.study_tidy

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.view.GestureDetectorCompat
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import android.widget.CalendarView
import android.widget.ImageButton
import android.widget.ListView
import android.widget.ArrayAdapter
import android.widget.Toast
import android.widget.TextView
import android.widget.AdapterView
import android.widget.EditText
import android.view.View.OnFocusChangeListener
import android.view.ViewGroup
import java.nio.file.Files.size
import android.content.Context.LAYOUT_INFLATER_SERVICE
import android.util.Log
import android.view.LayoutInflater
import android.widget.BaseAdapter
import com.google.gson.Gson
import com.prolificinteractive.materialcalendarview.CalendarUtils.getMonth
import kotlinx.android.synthetic.main.activity_day.*
import java.io.IOException
import java.io.OutputStreamWriter
import java.util.ArrayList


class day : Activity() {
    private var gestureDetectorCompat: GestureDetectorCompat? = null
    val monthList : List<String> = listOf("января", "февраля", "марта", "апреля", "мая", "июня", "июля",
            "августа", "сентября", "октября", "ноября", "декабря")
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_day)
        gestureDetectorCompat = GestureDetectorCompat(this, MyGestureListener())

        val extras = intent.extras
        var dateToAdd = ""

        if(extras.containsKey("Date")) {
            val date = extras.getString("Date")
            textView2.setText(date)
            var _date = date.split(" ")[0]
            var _month:Int = _getMonth(date.split(" ")[1]) + 1
            var year = date.split(" ")[2]
            dateToAdd = _date + " " + _month.toString() + " " + year.toString()
        }
        else
        {
            val day = extras.getString("day")
            val month = extras.getString("day_month")
            val year = extras.getString("day_year")
            textView2.setText(day + " " + monthList[month.toInt()] + " " + year)
            dateToAdd = day + " " + (month.toInt()+1).toString() + " " + year
        }

        if(DateStorage.dataStorage.isEmpty())
        {
            DateStorage.addEvent(dateToAdd, DateStorage.educationEvent, "")
            DateStorage.addEvent(dateToAdd, DateStorage.funEvent, "")
        }
        if(!DateStorage.dataStorage.containsKey(dateToAdd) || !DateStorage.dataStorage.get(dateToAdd)?.containsKey(DateStorage.educationEvent)!!) {
            DateStorage.addEvent(dateToAdd, DateStorage.educationEvent, "")
        }
        if(!DateStorage.dataStorage.containsKey(dateToAdd) ||!DateStorage.dataStorage.get(dateToAdd)?.containsKey(DateStorage.funEvent)!!)
        {
            DateStorage.addEvent(dateToAdd, DateStorage.funEvent, "")
        }

        studyListView.setItemsCanFocus(true)
        extracullicularListView.setItemsCanFocus(true)

        val studyAdapter = MyAdapter(applicationContext,  DateStorage.todayEvent(dateToAdd, DateStorage.educationEvent), DateStorage.educationEvent, dateToAdd)
        studyListView.setAdapter(studyAdapter);


        imageButton1.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View) {
                currentFocus?.clearFocus()
                DateStorage.addEvent(dateToAdd, DateStorage.educationEvent, "")
                studyAdapter.notifyDataSetChanged()
            }
        })

        val extracullicularAdapter = MyAdapter(applicationContext, DateStorage.todayEvent(dateToAdd, DateStorage.funEvent), DateStorage.funEvent, dateToAdd )
        extracullicularListView.setAdapter(extracullicularAdapter)

        imageButton.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View) {
                currentFocus?.clearFocus()
                DateStorage.addEvent(dateToAdd, DateStorage.funEvent, "")
                extracullicularAdapter.notifyDataSetChanged()
            }
        })
        studyAdapter.notifyDataSetChanged()
        extracullicularAdapter.notifyDataSetChanged()
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

    private fun _getMonth(s: String): Int {
        var t:Int = 0
        for(month in monthList)
        {
            if(s==month)
            {
                return t
            }
            t++
        }
        return 0
    }

    inner class MyAdapter(private var context: Context, evets: ArrayList<String>, private var direct: String, private var dateToAdd: String) : BaseAdapter() {
        private var events: ArrayList<String> = evets

        override fun getCount(): Int {
            return events.size
        }

        override fun getItem(position: Int): Any {
            return events.get(position)
        }

        override fun getItemId(position: Int): Long {
            return position.toLong()
        }

        @SuppressLint("ViewHolder", "InflateParams")
        override fun getView(position: Int, convertView: View?, parent: ViewGroup): View? {
            var retView = convertView
            lateinit var holder: ViewHolder
           // events = DateStorage.todayEvent(dateToAdd, direct)
            if (retView == null) {
                holder = ViewHolder()
                val mInflater: LayoutInflater
                mInflater = getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
                retView = mInflater.inflate(R.layout.item, null)

                holder.button = retView.findViewById(R.id.imageButton2) as ImageButton?
                holder.caption = retView.findViewById(R.id.ItemCaption) as EditText?
                retView.setTag(holder);
                }
            else
            {
                holder = convertView!!.getTag() as ViewHolder;
                retView = convertView
            }

            holder.caption?.setText(events.get(position));
            holder.caption?.onFocusChangeListener = OnFocusChangeListener { v, hasFocus ->
                if (!hasFocus) {
                    val Caption = v as EditText
                    DateStorage.setEvent(dateToAdd, direct, Caption.text.toString(), position)
                    notifyDataSetChanged()
                }
            }

            holder.button?.setOnClickListener(object : View.OnClickListener {
                override fun onClick(v: View) {
                    holder.caption?.clearFocus()
                    DateStorage.deleteEvent(dateToAdd, direct, position)
                    notifyDataSetChanged()
                }
            })

                return retView
        }
    }

    internal inner class ViewHolder {
        var caption: EditText? = null
        var button: ImageButton? = null
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

