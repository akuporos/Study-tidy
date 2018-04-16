package com.example.stasy.study_tidy

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
import java.util.ArrayList


class day : AppCompatActivity() {
    private var gestureDetectorCompat: GestureDetectorCompat? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_day)
        gestureDetectorCompat = GestureDetectorCompat(this, MyGestureListener())
        var image_button = findViewById(R.id.imageButton) as ImageButton

        val studyListView = findViewById<ListView>(R.id.listView)
        val extracullicularListView = findViewById(R.id.listView1) as ListView

        studyListView.setItemsCanFocus(true)
        extracullicularListView.setItemsCanFocus(true)

        val studyStorage = DateStorage()

        val date = "///"

        val studyAdapter = MyAdapter()
        studyListView.setAdapter(studyAdapter);

        val extracullicularAdapter = MyAdapter();
        extracullicularListView.setAdapter(extracullicularAdapter);

        image_button.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View) {

            }
        })
    }

    inner class MyAdapter : BaseAdapter() {
        private val mInflater: LayoutInflater
        internal var myItems = ArrayList<ListItem>()

        init {
            mInflater = getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            val listItem = ListItem()
            listItem.caption = "Caption$0"
            myItems.add(listItem)

            notifyDataSetChanged()
        }

        override fun getCount(): Int {
            return myItems.size
        }

        override fun getItem(position: Int): Any {
            return position
        }

        override fun getItemId(position: Int): Long {
            return position.toLong()
        }

        override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
            var convertView = convertView
            val holder: ViewHolder
            if (convertView == null) {
                holder = ViewHolder()
                convertView = mInflater.inflate(R.layout.item, null)
                holder.caption = convertView!!
                        .findViewById(R.id.ItemCaption) as EditText

                convertView.tag = holder
            } else {
                holder = convertView.tag as ViewHolder
            }
            //Fill EditText with the value you have in data source
            holder.caption!!.setText(myItems.get(position).caption)
            holder.caption!!.id = position

            //we need to update adapter once we finish with editing
            holder.caption!!.onFocusChangeListener = OnFocusChangeListener { v, hasFocus ->
                if (!hasFocus) {
                    val position = v.id
                    val Caption = v as EditText
                    myItems.get(position).caption = Caption.text.toString()
                }
            }

            return convertView
        }

    }

    internal inner class ViewHolder {
        var caption: EditText? = null
    }

    internal inner class ListItem {
        var caption: String? = null
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
