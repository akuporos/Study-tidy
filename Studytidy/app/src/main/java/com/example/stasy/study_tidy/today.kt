package com.example.stasy.study_tidy

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ListView
import java.util.ArrayList

class today : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_today)
        var image_button = findViewById(R.id.imageButton) as ImageButton
        image_button.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View) {
                finish()
            }
        })
        val studyListView = findViewById<ListView>(R.id.listView)
        val extracullicularListView = findViewById<ListView>(R.id.listView1)

       // val studyAdapter = MyAdapter()
       // studyListView.setAdapter(studyAdapter);
       // val extracullicularAdapter = MyAdapter( )
       // extracullicularListView.setAdapter(extracullicularAdapter)
    }

    inner class MyAdapter : BaseAdapter {
        private var context: Context
        private var events: ArrayList<String>//studyStorage.todayEvent("8", "Учебная")

        constructor(context: Context, events: ArrayList<String>) {
            this.context = context;
            this.events = events;
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

        override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
            var convertView = convertView

            if (convertView == null) {
                val mInflater: LayoutInflater
                mInflater = getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
                convertView = mInflater.inflate(R.layout.item1, null)

                var textView = convertView!!.findViewById(R.id.ItemCaption) as EditText


            }
            return convertView
        }
    }
}
