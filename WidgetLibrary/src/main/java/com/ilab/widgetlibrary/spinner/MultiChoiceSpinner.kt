package com.ilab.widgetlibrary.spinner

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.constraintlayout.widget.ConstraintLayout
import com.ilab.widgetlibrary.R

class MultiChoiceSpinner(context: Context, val attrs: AttributeSet) :
    LinearLayoutCompat(context, attrs) {
    private val llRoot: ConstraintLayout
    private val llListt: LinearLayout
    private val tvHint: TextView
    private val ivTips: ImageView
    private var popup: MultiChoiceSpinnerPopup? = null
    private var indexList:MutableSet<Int> = mutableSetOf()
    var list = mutableListOf("xx", "xxx", "xxxx", "xxxx", "xxxx", "xxxx")
    var onItemClick: (positionSet: MutableSet<Int>, dataSet: MutableSet<String>) -> Unit = { _, _ -> }

    fun getIndexList(): MutableSet<Int> {
        return indexList
    }

    fun setSelectIndexs(indexList:MutableSet<Int>){
        this.indexList = indexList
        popup?.setCheckedItem(indexList)
        addViewList(indexList)
    }


    private val inflater = LayoutInflater.from(context)

    init {
        inflater.inflate(R.layout.layout_multi_choice_spinner, this, true)
        llRoot = findViewById(R.id.clRoot)
        llListt = findViewById(R.id.llList)
        tvHint = findViewById(R.id.tvHint)
        ivTips = findViewById(R.id.ivTips)

        initPopup()
        initAttributes()
    }

    private fun initAttributes() {
        val styledAttributes = context.obtainStyledAttributes(attrs, R.styleable.MySpinner)
    }

    private fun initPopup() {
        if (popup == null) {
            popup = MultiChoiceSpinnerPopup(context, {
                Log.i("选中下标", "$it")
                tvHint.visibility = if (it.size > 0) View.GONE else View.VISIBLE

                addViewList(it)
                onItemClick(it, it.map { list[it] }.toMutableSet())
            }, {
                ivTips.setImageResource(R.drawable.ic_lower_triangle_blue)
                Log.i("弹框关闭", "xxxx")
            }).setWidth(llRoot.measuredWidth)
        }

        llRoot.setOnClickListener {
            ivTips.setImageResource(R.drawable.ic_triangle_upper_blue)
            popup?.setWidth(llRoot.measuredWidth)
            popup?.show(llRoot, 0, 12)
        }
    }

    private fun addViewList(it: MutableSet<Int>) {
        llListt.removeAllViews()
        tvHint.visibility = if (it.size > 0) View.GONE else View.VISIBLE
        it.forEach {
            val view =
                inflater.inflate(R.layout.layout_multi_choice_spinner_text_item, null)
            view.findViewById<TextView>(R.id.tvText).text = list[it]
            llListt.addView(view)
        }
        popup?.setCheckedItem(it)
    }

    fun clearSelected() {
        indexList.clear()
        llListt.removeAllViews()
        popup?.setCheckedItem(indexList)
    }

    fun setData(list: MutableList<String>) {
        this.list = list
        popup?.setData(list)
    }

}