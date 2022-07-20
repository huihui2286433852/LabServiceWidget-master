package com.ilab.widgetlibrary.spinner

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.annotation.ColorInt
import androidx.annotation.ColorRes
import androidx.appcompat.widget.LinearLayoutCompat
import com.ilab.widgetlibrary.R
import com.ilab.widgetlibrary.utils.DensityUtils
import com.ilab.widgetlibrary.utils.px2dip

class SingleChoiceSpinner(context: Context, private val attrs: AttributeSet) :
    LinearLayoutCompat(context, attrs) {
    private val tvText: TextView
    private val ivArrow: ImageView
    private var llRootView: LinearLayout
    private var popup: SpinnerPopup? = null
    private var index: Int = -1
    private var arrowColor: Int = 0xFF57ACFF.toInt()
    private var spinnerTextColor: Int = 0xff2a4283.toInt()
    private var spinnerTextCheckColor: Int = 0xff57acff.toInt()
    private var textSize: Float = 24f //这里但单位是dp
    private var spinnerBg: Drawable? = null
    private var spinnerTextPadding: Float? = null
    var list = mutableListOf("xx", "xxx", "xxxx")
    var onItemClick: (position: Int, data: String) -> Unit = { _, _ -> }
    fun getIndex(): Int {
        return index
    }

    init {
        LayoutInflater.from(context).inflate(R.layout.layout_my_spinner, this, true)
        llRootView = findViewById(R.id.llRootView)
        tvText = findViewById(R.id.tvText)
        ivArrow = findViewById(R.id.ivArrow)
        initAttributes()
        initPopup()
    }

    private fun initAttributes() {
        val styledAttributes = context.obtainStyledAttributes(attrs, R.styleable.MySpinner)
        val textColor =
            styledAttributes.getColor(R.styleable.MySpinner_text_color, 0xFF2A4283.toInt())
        textSize = DensityUtils.px2dip(
            context,
            styledAttributes.getDimension(R.styleable.MySpinner_text_size, 32F)
        ).toFloat()
        Log.i("xxxxxxxxxxxxxx", "==${textSize}")
        val hint = styledAttributes.getString(R.styleable.MySpinner_hint)
        val hintColor =
            styledAttributes.getColor(R.styleable.MySpinner_hint_color, 0xFF57ACFF.toInt())

        arrowColor =
            styledAttributes.getColor(R.styleable.MySpinner_arrow_color, 0xFF57ACFF.toInt())

        val textBg = styledAttributes.getDrawable(R.styleable.MySpinner_text_bg)

        spinnerBg = styledAttributes.getDrawable(R.styleable.MySpinner_spinner_bg)

        spinnerTextPadding =
            styledAttributes.getDimension(R.styleable.MySpinner_spinner_text_padding, 12F)


        spinnerTextColor =
            styledAttributes.getColor(R.styleable.MySpinner_spinner_text_color, 0xFF57ACFF.toInt())
        spinnerTextCheckColor =
            styledAttributes.getColor(
                R.styleable.MySpinner_spinner_text_check_color,
                0xFF57ACFF.toInt()
            )

        if (textBg != null) {
            llRootView.setBackgroundDrawable(textBg)
        }

        tvText.setTextColor(textColor)
        tvText.textSize = textSize
        tvText.hint = hint ?: "请选择"
        tvText.setHintTextColor(hintColor)
        ivArrow.setImageResource(R.drawable.ic_lower_triangle_blue)
        ivArrow.setColorFilter(arrowColor)
    }

    private fun initPopup() {
        if (popup == null) {
            popup = SpinnerPopup(
                context,
                spinnerBg,
                spinnerTextColor,
                spinnerTextCheckColor,
                textSize,
                {
                    Log.i("选中下标", "$it")
                    index = it
                    val data = list.getOrElse(it) { "" }
                    tvText.text = data
                    onItemClick.invoke(it, data)
                },
                {
                    ivArrow.setImageResource(R.drawable.ic_lower_triangle_blue)
                    ivArrow.setColorFilter(arrowColor)
                    Log.i("弹框关闭", "xxxx")
                }).setWidth(llRootView.measuredWidth)
        }

        llRootView.setOnClickListener {
            ivArrow.setImageResource(R.drawable.ic_triangle_upper_blue)
            ivArrow.setColorFilter(arrowColor)
            popup?.setWidth(llRootView.measuredWidth)
            popup?.show(llRootView, 0, spinnerTextPadding?.toInt() ?: 12)
        }
    }

    fun clearSelected() {
        index = -1
        tvText.text = ""
        popup?.setCheckedItem(-1)
    }

    fun setData(list: MutableList<String>) {
        this.list = list
        popup?.setData(list)
    }

    /**
     * 设置文本框字体颜色
     */
    fun setTextColor(@ColorInt color: Int) {
        tvText.setTextColor(color)
    }

    fun setTextColorRes(@ColorRes color: Int) {
        tvText.setTextColor(context.getColor(color))
    }

    /**
     * 设置文本（文本框+下拉框文本）字体大小
     */
    fun setTextSize(size: Float) {
        textSize = size
        tvText.textSize = textSize
        popup?.setPopTextSize(size)

    }

    /**
     * 设置提示文字
     */
    fun setHint(hint: String) {
        tvText.hint = hint
    }

    fun setHintColor(@ColorInt color: Int) {
        tvText.setHintTextColor(color)
    }

    fun setHintColorRes(@ColorRes color: Int) {
        tvText.setHintTextColor(context.getColor(color))
    }

    /**
     * 设置箭头颜色
     */
    fun setArrowColor(@ColorInt color: Int) {
        arrowColor = color
        ivArrow.setColorFilter(arrowColor)
    }

    fun setArrowColorRes(@ColorRes color: Int) {
        arrowColor = context.getColor(color)
        ivArrow.setColorFilter(arrowColor)
    }

    /**
     * 设置文本框背景
     */
    fun setTextBg(textBg: Drawable) {
        llRootView.setBackgroundDrawable(textBg)
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
    fun setSpinnerTextPadding(padding:Float){
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