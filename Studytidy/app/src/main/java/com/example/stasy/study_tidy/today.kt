package com.example.stasy.study_tidy

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageButton

class today : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_today)
        var image_button = findViewById(R.id.imageButton) as ImageButton
        image_button.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View) {
                finish()
            }
        })

    }
}
