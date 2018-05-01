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
import com.google.gson.Gson
import android.R.attr.data
import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.preference.PreferenceDataStore
import android.util.Log
import java.io.*

class MainActivity : Activity() {

    private var gestureDetectorCompat: GestureDetectorCompat? = null
    var gson = Gson()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var content = readFromFile(applicationContext)


        if(DateStorage.dataStorage.isEmpty() && content != "") {
            gson.fromJson(content, DateStorage::class.java)
        }

        var image_button = findViewById(imageButton) as ImageButton
        image_button.setOnClickListener(object : View.OnClickListener {
           override fun onClick(v: View) {
               val scrollingActivity = Intent(applicationContext, today::class.java)
               startActivity(scrollingActivity);
            }
        })
        gestureDetectorCompat = GestureDetectorCompat(this, MyGestureListener())
    }

    override fun onDestroy() {
        super.onDestroy()
        val json = gson.toJson(DateStorage)
        try {
            val outputStreamWriter = OutputStreamWriter(applicationContext.openFileOutput(DateStorage.filename, Context.MODE_PRIVATE))
            outputStreamWriter.write(json)
            outputStreamWriter.close()
        } catch (e: IOException) {
            Log.e("Exception", "File write failed: " + e.toString())
        }
    }

    private fun readFromFile(context: Context): String {
        var ret = ""
        try {
            val inputStream = context.openFileInput(DateStorage.filename)
            if (inputStream != null) {
                val inputStreamReader = InputStreamReader(inputStream)
                val bufferedReader = BufferedReader(inputStreamReader)
                var receiveString = ""
                val stringBuilder = StringBuilder()
                //receiveString = bufferedReader.readLine().toString()
                ret = inputStream.bufferedReader().use(BufferedReader::readText)

              //  while ((sCurrentLine = bufferedReader.readLine()) != null) println(sCurrentLine)
          /*      while (receiveString != null) {
                    stringBuilder.append(receiveString)
                    receiveString = bufferedReader.readLine()
                }

                inputStream.close()
                ret = stringBuilder.toString()
            */}
        } catch (e: FileNotFoundException) {
            Log.e("login activity", "File not found: " + e.toString())
        } catch (e: IOException) {
            Log.e("login activity", "Can not read file: " + e.toString())
        }
        return ret
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
