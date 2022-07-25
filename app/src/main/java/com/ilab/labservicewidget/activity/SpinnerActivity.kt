package com.ilab.labservicewidget.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.ilab.labservicewidget.R
import com.ilab.widgetlibrary.spinner.MultiChoiceSpinner
import com.ilab.widgetlibrary.spinner.SingleChoiceSpinner

class SpinnerActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_spinner)
        initView()
    }

    private fun initView() {
        val mySpinner = findViewById<SingleChoiceSpinner>(R.id.mySpinner)
        mySpinner.setData(mutableListOf("Java","Android","微信小程序","Flutter","ReactNative"))
        mySpinner.setTextColor(0xff00ff00.toInt())
        mySpinner.setTextSize(20f)
        mySpinner.setHint("嘻嘻嘻")
        mySpinner.setHintColor(0xff00ff00.toInt())
        mySpinner.setArrowColor(0xff00ff00.toInt())
        mySpinner.setTextBg(resources.getDrawable(R.drawable.bb))
        mySpinner.setSpinnerTextPadding(0f)
        mySpinner.setPopSpinnerBg(resources.getDrawable(R.drawable.bb))
        mySpinner.setPopSpinnerTextColor(0xff00ff00.toInt())
        mySpinner.setPopSpinnerTextCheckColor(0xff00ff00.toInt())

        val myMultiSpinner = findViewById<MultiChoiceSpinner>(R.id.myMultiSpinner)
        myMultiSpinner.setData(mutableListOf("Java","Android","微信小程序","Flutter","ReactNative"))
        myMultiSpinner.setTextColor(0xff00ff00.toInt())
        myMultiSpinner.setTextSize(20f)
        myMultiSpinner.setHint("嘻嘻嘻")
        myMultiSpinner.setHintColor(0xff00ff00.toInt())
        myMultiSpinner.setArrowColor(0xff00ff00.toInt())
        myMultiSpinner.setTextBg(resources.getDrawable(R.drawable.bb))
        myMultiSpinner.setTextItemBg(resources.getDrawable(R.drawable.bb))
        myMultiSpinner.setSpinnerTextPadding(0f)
        myMultiSpinner.setPopSpinnerBg(resources.getDrawable(R.drawable.bb))
        myMultiSpinner.setPopSpinnerTextColor(0xff00ff00.toInt())
        myMultiSpinner.setPopSpinnerTextCheckColor(0xff00ff00.toInt())

    }
}