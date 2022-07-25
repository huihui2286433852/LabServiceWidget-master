package com.ilab.widgetlibrary.spinner

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.annotation.ColorInt
import androidx.annotation.ColorRes
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.constraintlayout.widget.ConstraintLayout
import com.ilab.widgetlibrary.R
import com.ilab.widgetlibrary.utils.DensityUtils

class MultiChoiceSpinner(context: Context, val attrs: AttributeSet) :
    LinearLayoutCompat(context, attrs) {
    private val llRoot: ConstraintLayout
    private val llListt: LinearLayout
    private val tvHint: TextView
    private val ivTips: ImageView
    private var popup: MultiChoiceSpinnerPopup? = null
    private var indexList: MutableSet<Int> = mutableSetOf()

    private var textColor: Int = 0xFF57ACFF.toInt()
    private var arrowColor: Int = 0xFF57ACFF.toInt()
    private var spinnerTextColor: Int = 0xff2a4283.toInt()
    private var spinnerTextCheckColor: Int = 0xff57acff.toInt()
    private var textSize: Float = 24f //这里但单位是dp
    private var spinnerBg: Drawable? = null
    private var checkBoxButton: Drawable? = null
    private var spinnerTextPadding: Float? = null
    private var textItemBg: Drawable? = null

    var list = mutableListOf("xx", "xxx", "xxxx", "xxxx", "xxxx", "xxxx")
    var onItemClick: (positionSet: MutableSet<Int>, dataSet: MutableSet<String>) -> Unit =
        { _, _ -> }


    fun getIndexList(): MutableSet<Int> {
        return indexList
    }

    fun setSelectIndexs(indexList: MutableSet<Int>) {
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
        initAttributes()
        initPopup()
    }

    private fun initAttributes() {
        val styledAttributes = context.obtainStyledAttributes(attrs, R.styleable.MySpinner)

        textColor =
            styledAttributes.getColor(R.styleable.MySpinner_text_color, 0xFF2A4283.toInt())
        textSize = DensityUtils.px2dip(
            context,
            styledAttributes.getDimension(R.styleable.MySpinner_text_size, 32F)
        ).toFloat()
        val hint = styledAttributes.getString(R.styleable.MySpinner_hint)
        val hintColor =
            styledAttributes.getColor(R.styleable.MySpinner_hint_color, 0xFF57ACFF.toInt())

        arrowColor =
            styledAttributes.getColor(R.styleable.MySpinner_arrow_color, 0xFF57ACFF.toInt())

        val textBg = styledAttributes.getDrawable(R.styleable.MySpinner_text_bg)
        textItemBg = styledAttributes.getDrawable(R.styleable.MySpinner_text_item_bg)

        spinnerBg = styledAttributes.getDrawable(R.styleable.MySpinner_spinner_bg)
        checkBoxButton =
            styledAttributes.getDrawable(R.styleable.MySpinner_spinner_check_box_bottom)

        spinnerTextPadding =
            styledAttributes.getDimension(R.styleable.MySpinner_spinner_text_padding, 12F)


        spinnerTextColor =
            styledAttributes.getColor(R.styleable.MySpinner_spinner_text_color, 0xFF57ACFF.toInt())
        spinnerTextCheckColor =
            styledAttributes.getColor(
                R.styleable.MySpinner_spinner_text_check_color,
                0xFF57ACFF.toInt()
            )


        tvHint.text = hint
        tvHint.textSize = textSize
        tvHint.setTextColor(hintColor)
        llRoot.setBackgroundDrawable(textBg)


    }

    private fun initPopup() {
        if (popup == null) {
            popup =
                MultiChoiceSpinnerPopup(context,
                    spinnerBg,
                    checkBoxButton,
                    spinnerTextColor,
                    spinnerTextCheckColor,
                    textSize, {
                        Log.i("选中下标", "$it")
                        tvHint.visibility = if (it.size > 0) View.GONE else View.VISIBLE

                        addViewList(it)
                        onItemClick(it, it.map { list[it] }.toMutableSet())
                    }, {
                        ivTips.setImageResource(R.drawable.ic_lower_triangle_blue)
                        ivTips.setColorFilter(arrowColor)
                        Log.i("弹框关闭", "xxxx")
                    }).setWidth(llRoot.measuredWidth)
        }

        llRoot.setOnClickListener {
            ivTips.setImageResource(R.drawable.ic_triangle_upper_blue)
            ivTips.setColorFilter(arrowColor)
            popup?.setWidth(llRoot.measuredWidth)
            popup?.show(llRoot, 0, spinnerTextPadding?.toInt() ?: 0)
        }
    }

    private fun addViewList(it: MutableSet<Int>) {
        llListt.removeAllViews()
        tvHint.visibility = if (it.size > 0) View.GONE else View.VISIBLE
        it.forEach {
            val view =
                inflater.inflate(R.layout.layout_multi_choice_spinner_text_item, null)
            var tvText = view.findViewById<TextView>(R.id.tvText)
            tvText.text = list[it]
            tvText.setTextColor(textColor)
            tvText.textSize = textSize
            if (textItemBg != null) {
                tvText.setBackgroundDrawable(textItemBg)
            }
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


    /**
     * 设置文本框字体颜色
     */
    fun setTextColor(@ColorInt color: Int) {
        textColor = color
        addViewList(indexList)
    }

    fun setTextColorRes(@ColorRes color: Int) {
        textColor = context.getColor(color)
        addViewList(indexList)
    }

    /**
     * 设置文本（文本框+下拉框文本）字体大小
     */
    fun setTextSize(size: Float) {
        textSize = size
        tvHint.textSize = textSize
        addViewList(indexList)
        popup?.setPopTextSize(size)
    }

    /**
     * 设置提示文字
     */
    fun setHint(hint: String) {
        tvHint.text = hint
    }

    fun setHintColor(@ColorInt color: Int) {
        tvHint.setTextColor(color)
    }

    fun setHintColorRes(@ColorRes color: Int) {
        tvHint.setTextColor(context.getColor(color))
    }

    /**
     * 设置箭头颜色
     */
    fun setArrowColor(@ColorInt color: Int) {
        arrowColor = color
        ivTips.setColorFilter(arrowColor)
    }

    fun setArrowColorRes(@ColorRes color: Int) {
        arrowColor = context.getColor(color)
        ivTips.setColorFilter(arrowColor)
    }

    /**
     * 设置文本框背景
     */
    fun setTextBg(textBg: Drawable) {
        llRoot.setBackgroundDrawable(textBg)
    }

    /**
     * 文本框中选中条目背景
     */
    fun setTextItemBg(bg: Drawable) {
        textItemBg = bg
        addViewList(indexList)
    }


    /**
     * 设置弹框背景
     */
    fun setPopSpinnerBg(bg: Drawable) {
        popup?.setPopSpinnerBg(bg)
    }

    /**
     *弹窗与顶部文本框间距
     */
    fun setSpinnerTextPadding(padding: Float) {
        spinnerTextPadding = padding
    }

    /**
     * 设置文本颜色
     */
    fun setPopSpinnerTextColor(@ColorInt color: Int) {
        popup?.setPopSpinnerTextColor(color)
    }

    fun setPopSpinnerTextColorRes(@ColorRes color: Int) {
        popup?.setPopSpinnerTextColor(context.getColor(color))
    }

    /**
     * 设置选中文本颜色
     */
    fun setPopSpinnerTextCheckColor(@ColorInt color: Int) {
        popup?.setPopSpinnerTextCheckColor(color)
    }

    fun setPopSpinnerTextCheckColorRes(@ColorRes color: Int) {
        popup?.setPopSpinnerTextCheckColor(context.getColor(color))
    }
}