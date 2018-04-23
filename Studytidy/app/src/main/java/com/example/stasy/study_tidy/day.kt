package com.example.stasy.study_tidy

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
import android.view.LayoutInflater
import android.widget.BaseAdapter
import com.prolificinteractive.materialcalendarview.CalendarUtils.getMonth
import kotlinx.android.synthetic.main.activity_day.*
import java.util.ArrayList


class day : Activity() {
    private var gestureDetectorCompat: GestureDetectorCompat? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_day)
        gestureDetectorCompat = GestureDetectorCompat(this, MyGestureListener())

        val extras = intent.extras
        val date = extras.getString("Date")

        textView2.setText(date)

        var _date = date.split(" ")[0]
        var _month:Int = _getMonth(date.split(" ")[1])
        var dateToAdd = _date + " " + _month.toString()

        val studyListView = findViewById<ListView>(R.id.listView)
        val extracullicularListView = findViewById<ListView>(R.id.listView1)

        studyListView.setItemsCanFocus(true)
        extracullicularListView.setItemsCanFocus(true)
        val studyAdapter = MyAdapter(applicationContext, DateStorage.educationEvent, dateToAdd)
        studyListView.setAdapter(studyAdapter);

        imageButton1.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View) {
                DateStorage.addEvent(dateToAdd, DateStorage.educationEvent, "")
                studyAdapter.notifyDataSetChanged()
            }
        })

        val extracullicularAdapter = MyAdapter(applicationContext,DateStorage.funEvent, dateToAdd )
        extracullicularListView.setAdapter(extracullicularAdapter)

        imageButton.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View) {
                DateStorage.addEvent(dateToAdd, DateStorage.funEvent, "")
                extracullicularAdapter.notifyDataSetChanged()
            }
        })
    }

    private fun _getMonth(s: String): Int {
        val monthList : List<String> = listOf("января", "февраля", "марта", "апреля", "мая", "июня", "июля",
                "августа", "сентября", "октября", "ноября", "декабря")
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

    inner class MyAdapter : BaseAdapter {
        private var context: Context
        private var direct: String
        private var dateToAdd: String
        private var events: ArrayList<String> = ArrayList<String>()
        constructor(context: Context, direct : String, dateToAdd: String) {
            this.context = context;
            this.direct = direct;
            this.dateToAdd = dateToAdd
        }

        override fun getCount(): Int {
            return events.size
        }

        override fun getItem(position: Int): Any {
            return position
        }

        override fun getItemId(position: Int): Long {
            return position.toLong()
        }

        override fun getView(position: Int, convertView: View, parent: ViewGroup): View {
            var retView = convertView
            val holder: ViewHolder = ViewHolder()
            events = DateStorage.todayEvent(dateToAdd, direct)
            if (convertView == null) {
                val mInflater: LayoutInflater
                mInflater = getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
                retView = mInflater.inflate(R.layout.item, null)

                holder.button = convertView.findViewById(R.id.imageButton2) as ImageButton
                holder.caption = convertView.findViewById(R.id.ItemCaption) as EditText
                retView.setTag(holder);
                }
            else
            {
                convertView.setTag(holder);
                retView = convertView
            }
            holder.caption?.setText(events.get(position));
            holder.caption?.setOnFocusChangeListener(OnFocusChangeListener { v, hasFocus ->
                if (!hasFocus) {
                    val position = v.id
                    val Caption = v as EditText
                    DateStorage.changeEvent(dateToAdd, direct, Caption.text.toString(), position)
                    notifyDataSetChanged()
                }
            })

                holder.button?.setOnClickListener(object : View.OnClickListener {
                    override fun onClick(v: View) {
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

