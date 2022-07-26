package com.ilab.widgetlibrary.spinner

import android.content.Context
import android.graphics.PorterDuff
import android.graphics.drawable.Drawable
import android.os.Build
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import android.widget.CheckBox
import android.widget.PopupWindow
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.ilab.widgetlibrary.R

class MultiChoiceSpinnerPopup(
    var context: Context,
    var spinnerBg: Drawable?,
    var checkBoxButton:Drawable?,
    var spinnerTextColor: Int,
    var spinnerTextCheckColor: Int,
    var textSize: Float,
    var onItemClick: (list: MutableSet<Int>) -> Unit,
    var onPopupDismiss: () -> Unit
) {
    var root: View = LayoutInflater.from(context).inflate(R.layout.layout_spinner_popup, null)
    private var popupWindow: PopupWindow = PopupWindow(context)
    private var selectList = mutableSetOf<Int>()
    private var mAdapter =
        MultiChoicePopupAdapter(selectList, checkBoxButton,spinnerTextColor, spinnerTextCheckColor, textSize) {
            onItemClick(it)
        }

    init {
        popupWindow.contentView = root
        popupWindow.isOutsideTouchable = true
        popupWindow.isFocusable = true
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            popupWindow.setBackgroundDrawable(context.getDrawable(R.drawable.bg_white_ff57acff_line_fillet10))
        } else {
            popupWindow.setBackgroundDrawable(
                context.getDrawable(
                    R.drawable.ms__drop_down_shadow
                )
            )
        }

        if (spinnerBg != null) {
            root.setBackgroundDrawable(spinnerBg)
        }

        //popup 关闭监听
        popupWindow.setOnDismissListener {
            onPopupDismiss()
        }

        var rv = root.findViewById<RecyclerView>(R.id.rvRecyclerView)
        rv.layoutManager = LinearLayoutManager(context)
        rv.adapter = mAdapter
        popupWindow.width = WindowManager.LayoutParams.WRAP_CONTENT
        popupWindow.height = WindowManager.LayoutParams.WRAP_CONTENT
        popupWindow.background.setColorFilter(
            context.getColor(android.R.color.white),
            PorterDuff.Mode.SRC_IN
        )
    }

    fun setData(list: MutableList<String>): MultiChoiceSpinnerPopup {
        mAdapter.setList(list)
        return this
    }

    fun setWidth(width: Int): MultiChoiceSpinnerPopup {
        popupWindow.width = width
        return this
    }

    fun setHeight(height: Int): MultiChoiceSpinnerPopup {
        popupWindow.height = height
        return this
    }

    fun setCheckedItem(selectList: MutableSet<Int>): MultiChoiceSpinnerPopup {
        this.selectList = selectList
        mAdapter.selectList = selectList
        mAdapter.notifyDataSetChanged()
        return this
    }

    fun show(view: View, xoff: Int, yoff: Int): MultiChoiceSpinnerPopup {
        popupWindow.showAsDropDown(view, xoff, yoff)
        return this
    }

    fun dismiss() {
        popupWindow.dismiss()
    }


    /**
     * 设置弹框背景
     */
    fun setPopSpinnerBg(bg: Drawable) {
        spinnerBg = bg
        if (spinnerBg != null) {
            root.setBackgroundDrawable(spinnerBg)
        }
    }

    /**
     * 设置文本颜色
     */
    fun setPopSpinnerTextColor(color: Int) {
        spinnerTextColor = color
        mAdapter.spinnerTextColor = color
        mAdapter.notifyDataSetChanged()
    }

    /**
     * 设置选中文本颜色
     */
    fun setPopSpinnerTextCheckColor(color: Int) {
        spinnerTextCheckColor = color
        mAdapter.spinnerTextCheckColor = color
        mAdapter.notifyDataSetChanged()
    }

    /**
     * 设置选中文本颜色
     */
    fun setPopTextSize(size: Float) {
        textSize = size
        mAdapter.textSize = size
        mAdapter.notifyDataSetChanged()
    }
}

class MultiChoicePopupAdapter(
    var selectList: MutableSet<Int>,
    var checkBoxButton:Drawable?,
    var spinnerTextColor: Int,
    var spinnerTextCheckColor: Int,
    var textSize: Float,
    var onItemClick: (list: MutableSet<Int>) -> Unit,
) :
    BaseQuickAdapter<String, BaseViewHolder>(R.layout.item_multi_choice_spinner_popup) {
    override fun convert(holder: BaseViewHolder, item: String) {
        var checkBox = holder.getView<CheckBox>(R.id.cbMsg)
        checkBox.text = item
        Log.i("xxxxxx", "${holder.layoutPosition}")
        checkBox.isChecked = selectList.contains(holder.layoutPosition)
        checkBox.textSize = textSize

        checkBox.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                selectList.add(holder.layoutPosition)
            } else {
                selectList.remove(holder.layoutPosition)
            }
            onItemClick(selectList)
        }

        if (checkBox.isChecked) {
            checkBox.setTextColor(spinnerTextCheckColor)
        } else {
            checkBox.setTextColor(spinnerTextColor)
        }
        if(checkBoxButton != null){
            checkBox.buttonDrawable = checkBoxButton
        }
    }
}