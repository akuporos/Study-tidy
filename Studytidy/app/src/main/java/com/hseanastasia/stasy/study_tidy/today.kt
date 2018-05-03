package com.hseanastasia.stasy.study_tidy

import android.annotation.SuppressLint
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
import android.widget.ArrayAdapter




class today : Activity() {

    @SuppressLint("ResourceType")
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
        val mdformat = SimpleDateFormat("yyyy/MM/dd")

        var year = mdformat.format(calendar.time).split("/")[0]
        year = (year.toInt()).toString()

        var month = mdformat.format(calendar.time).split("/")[1]
        month = (month.toInt()).toString()

        var day = mdformat.format(calendar.time).split("/")[2]
        day = (day.toInt()).toString()

        val today_date = day + " " + month + " " + year
        val studyAdapter = ArrayAdapter<String>(this,
                R.layout.item1, R.id.todayItem, DateStorage.todayEvent(today_date, DateStorage.educationEvent))
        studyListView.setAdapter(studyAdapter);

        val extracullicularAdapter = ArrayAdapter<String>(this,
                R.layout.item1, R.id.todayItem, DateStorage.todayEvent(today_date, DateStorage.funEvent))
        extracullicularListView.setAdapter(extracullicularAdapter)

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
}
