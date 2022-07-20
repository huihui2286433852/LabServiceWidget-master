package com.ilab.labservicewidget

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import com.ilab.labservicewidget.activity.CircleProgressActivity
import com.ilab.labservicewidget.activity.SpinnerActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        findViewById<Button>(R.id.btn1).setOnClickListener {
            startActivity(Intent(this,CircleProgressActivity::class.java))
        }
        findViewById<Button>(R.id.btn2).setOnClickListener {
            startActivity(Intent(this,SpinnerActivity::class.java))
        }
    }


}