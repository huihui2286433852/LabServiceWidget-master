package com.ilab.labservicewidget.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.CheckBox
import com.ilab.labservicewidget.R
import com.ilab.widgetlibrary.CircleProgressView

class CircleProgressActivity : AppCompatActivity() {
    lateinit var progressView: CircleProgressView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_circle_progress)
        initView()
    }

    private fun initView() {
        progressView = findViewById(R.id.cpvProgressView)
        //显示外环
        findViewById<CheckBox>(R.id.cb1).setOnCheckedChangeListener { _, check ->
            progressView.setShowOutCircle(check)
            progressView.progress = 0f
            if (check) {
                progressView.setProgressWithAnimation(100f, 1000)
            }
        }
        //显示中心填充
        findViewById<CheckBox>(R.id.cb2).setOnCheckedChangeListener { _, check ->
            progressView.setShowInnerCircle(check)
            progressView.progress = 0f
            if (check) {
                progressView.setProgressWithAnimation(100f, 1000)
            }
        }
        //显示渐变色
        findViewById<CheckBox>(R.id.cb3).setOnCheckedChangeListener { _, check ->
            progressView.setProgressGradient(check)
            progressView.progress = 0f
            if (check) {
                progressView.progressStartColor = 0xff00ff00.toInt()
                progressView.progressEndColor = 0xffff0000.toInt()
                progressView.setProgressWithAnimation(100f, 1000)
            } else {
                progressView.circleColor = 0xffff0000.toInt()
            }

        }
    }
}