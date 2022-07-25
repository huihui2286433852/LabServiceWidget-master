package com.ilab.widgetlibrary.spinner

import android.content.Context
import android.graphics.Color
import android.graphics.PorterDuff
import android.graphics.drawable.Drawable
import android.os.Build
import android.util.Log
import android.util.Size
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import android.widget.PopupWindow
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.ilab.widgetlibrary.R

class SpinnerPopup(
    context: Context,
    var spinnerBg: Drawable?,
    var spinnerTextColor: Int,
    var spinnerTextCheckColor: Int,
    var textSize: Float,
    var onItemClick: (position: Int) -> Unit,
    var onPopupDismiss: () -> Unit
) {
    var root: View = LayoutInflater.from(context).inflate(R.layout.layout_spinner_popup, null)
    private var popupWindow: PopupWindow = PopupWindow(context)
    var position: Int = -1
    var mAdapter = PopupAdapter(position, spinnerTextColor, spinnerTextCheckColor, textSize)

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
        //popup 关闭监听
        popupWindow.setOnDismissListener {
            onPopupDismiss()
        }

        var rv = root.findViewById<RecyclerView>(R.id.rvRecyclerView)
        rv.layoutManager = LinearLayoutManager(context)
        rv.adapter = mAdapter
        rv.setHasFixedSize(true)
        mAdapter.setOnItemClickListener { _, _, position ->
            mAdapter.position = position
            mAdapter.notifyDataSetChanged()
            onItemClick(position)
            dismiss()
        }
        if (spinnerBg != null) {
            root.setBackgroundDrawable(spinnerBg)
        }

        popupWindow.width = WindowManager.LayoutParams.WRAP_CONTENT
        popupWindow.height = WindowManager.LayoutParams.WRAP_CONTENT
        popupWindow.background.setColorFilter(
            context.getColor(android.R.color.white),
            PorterDuff.Mode.SRC_IN
        )
    }

    fun setData(list: MutableList<String>): SpinnerPopup {
        mAdapter.setList(list)
        return this
    }

    fun setWidth(width: Int): SpinnerPopup {
        popupWindow.width = width
        return this
    }

    fun setHeight(height: Int): SpinnerPopup {
        popupWindow.height = height
        return this
    }

    fun setCheckedItem(position: Int): SpinnerPopup {
        this.position = position
        mAdapter.position = position
        mAdapter.notifyDataSetChanged()
        return this
    }

    fun show(view: View, xoff: Int, yoff: Int): SpinnerPopup {
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

class PopupAdapter(
    var position: Int,
    var spinnerTextColor: Int,
    var spinnerTextCheckColor: Int,
    var textSize: Float,
) :
    BaseQuickAdapter<String, BaseViewHolder>(R.layout.item_spinner_popup) {
    override fun convert(holder: BaseViewHolder, item: String) {
        var textView = holder.getView<TextView>(R.id.tvMsg)
        textView.text = item
        textView.textSize = textSize
        Log.i("xxxxxx", "${holder.layoutPosition}--${position}")
        if (holder.layoutPosition == position) {
            textView.setTextColor(spinnerTextCheckColor)
        } else {
            textView.setTextColor(spinnerTextColor)
        }
    }
}