package com.example.stasy.study_tidy

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.google.gson.Gson
import java.io.IOException
import java.io.OutputStreamWriter
import java.util.*
import java.text.SimpleDateFormat


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

        val calendar = Calendar.getInstance()
        val mdformat = SimpleDateFormat("yyyy/MM/dd ")
        var month = mdformat.format(calendar.time).split("/")[1]
        month = (month.toInt() - 1).toString()

        var day = mdformat.format(calendar.time).split("/")[2]
        month = (month.toInt()).toString()

        val today_date = day + " " + month
        val studyAdapter = MyAdapter(applicationContext, DateStorage.todayEvent(today_date, DateStorage.educationEvent))
        studyListView.setAdapter(studyAdapter);
        val extracullicularAdapter = MyAdapter(applicationContext, DateStorage.todayEvent(today_date, DateStorage.funEvent) )
        extracullicularListView.setAdapter(extracullicularAdapter)

    }
    override fun onDestroy() {
        super.onDestroy()
        var gson = Gson()
        val json = gson.toJson(DateStorage)
        try {
            val outputStreamWriter = OutputStreamWriter(applicationContext.openFileOutput(DateStorage.filename, Context.MODE_PRIVATE))
            outputStreamWriter.write(json)
            outputStreamWriter.close()
        } catch (e: IOException) {
            Log.e("Exception", "File write failed: " + e.toString())
        }
    }
    inner class MyAdapter(private var context: Context,//studyStorage.todayEvent("8", "Учебная")
                          private var events: ArrayList<String>) : BaseAdapter() {

        override fun getCount(): Int {
            return events.size
        }

        override fun getItem(position: Int): Any {
            return events.get(position)
        }

        override fun getItemId(position: Int): Long {
            return position.toLong()
        }

        override fun getView(position: Int, convertView: View?, parent: ViewGroup): View? {
            var retView = convertView
            var caption: TextView? = null
            if(retView != null) {
                val mInflater: LayoutInflater
                mInflater = getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
                retView = mInflater.inflate(R.layout.item1, null)

                caption = retView.findViewById(R.id.ItemCaption) as TextView?
            }
            else
            {
                retView = convertView
            }
            caption?.setText(events.get(position));
            return retView
        }
    }
}
