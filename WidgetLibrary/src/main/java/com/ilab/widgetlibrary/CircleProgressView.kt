package com.ilab.widgetlibrary

import android.animation.Animator
import android.animation.ArgbEvaluator
import android.graphics.RectF
import android.widget.TextView
import android.widget.LinearLayout
import android.view.Gravity
import android.text.TextUtils
import android.animation.ObjectAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import kotlin.math.roundToInt

/**
 *
 * @author huanghui
 * @data 2022/7/12
 */
class CircleProgressView(context: Context, attrs: AttributeSet) : View(context, attrs) {
    private var mProgress = 0f
    private var mMaxProgress = 0f
    private var mOutsideRingWidth = 0f
    private var mCircleWidth = 0f
    private var backgroundStrokeWidth = 0f
    private var mCircleColor = 0
    private var mBackgroundColor = 0
    private var mInnerCircleColor = 0
    private var mOutsideRingColor = 0
    private var mFirstRectF: RectF? = null
    private var mSecondRectF: RectF? = null
    private var mBackgroundPaint: Paint? = null
    private var mCirclePaint: Paint? = null
    private var mInnerCirclePaint: Paint? = null
    private var mOutsideRingPaint: Paint? = null
    private var mIsTextEnabled = false
    private var isShowOutCircle = false
    private var isShowInnerCircle = false
    private var isProgressGradient = false //进度条颜色是否需要渐变 只有设置为true时 设置渐变颜色才生效
    private var mProgressStartColor = 0
    private var mProgressEndColor = 0
    private var mTextPrefix: String? = null
    private var mTextSuffix: String? = null

    var startAngle = 0f
    private lateinit var mTextView: TextView
    private lateinit var mTipTextView: TextView
    private var mTextColor = 0
    private var mTextSize = 0
    private var mTextBold = false
    private var mTipTextColor = 0
    private var mTipTextSize = 0
    private var mTipText: String? = null
    private lateinit var mLayout: LinearLayout

    //外环到内环的距离
    private var mFirstFromSecond = 0
    private var innerCircleX = 0f
    private var innerCircleY = 0f
    private var innerCircleRadius = 0f

    /**
     * 获取动画监听
     *
     * @return
     */
    var progressAnimationListener: ProgressAnimationListener? = null
        private set
    private val argbEvaluator = ArgbEvaluator() //颜色插值器（level 11以上才可以用）
    private fun init(context: Context, attrs: AttributeSet) {
        mFirstRectF = RectF()
        mSecondRectF = RectF()
        setDefaultValues()
        val typedArray =
            context.theme.obtainStyledAttributes(attrs, R.styleable.CircularProgressView, 0, 0)
        try {
            mProgress =
                typedArray.getFloat(R.styleable.CircularProgressView_cpv_progress, mProgress)
            mMaxProgress =
                typedArray.getFloat(R.styleable.CircularProgressView_cpv_max_progress, mMaxProgress)
            mFirstFromSecond = typedArray.getInteger(
                R.styleable.CircularProgressView_cpv_first_from_second,
                mFirstFromSecond
            )
            mCircleWidth = typedArray.getDimension(
                R.styleable.CircularProgressView_cpv_circle_width,
                mCircleWidth
            )
            mOutsideRingWidth = typedArray.getDimension(
                R.styleable.CircularProgressView_cpv_outside_ring_width,
                mOutsideRingWidth
            )
            backgroundStrokeWidth = typedArray.getDimension(
                R.styleable.CircularProgressView_cpv_background_circle_width,
                backgroundStrokeWidth
            )
            mCircleColor =
                typedArray.getInt(R.styleable.CircularProgressView_cpv_circle_color, mCircleColor)
            mInnerCircleColor = typedArray.getInt(
                R.styleable.CircularProgressView_cpv_inner_circle_color,
                mInnerCircleColor
            )
            mOutsideRingColor = typedArray.getInt(
                R.styleable.CircularProgressView_cpv_outside_ring_color,
                mOutsideRingColor
            )
            mBackgroundColor = typedArray.getInt(
                R.styleable.CircularProgressView_cpv_background_circle_color,
                mBackgroundColor
            )
            mTextColor =
                typedArray.getInt(R.styleable.CircularProgressView_cpv_text_color, mTextColor)
            mTextSize = typedArray.getInt(R.styleable.CircularProgressView_cpv_text_size, mTextSize)
            mTextBold =
                typedArray.getBoolean(R.styleable.CircularProgressView_cpv_text_bold, mTextBold)
            mTextPrefix = typedArray.getString(R.styleable.CircularProgressView_cpv_text_prefix)
            mTextSuffix = typedArray.getString(R.styleable.CircularProgressView_cpv_text_suffix)
            mTipTextColor = typedArray.getInt(
                R.styleable.CircularProgressView_cpv_tip_text_color,
                mTipTextColor
            )
            mTipTextSize =
                typedArray.getInt(R.styleable.CircularProgressView_cpv_tip_text_size, mTipTextSize)
            mTipText = typedArray.getString(R.styleable.CircularProgressView_cpv_tip_text)
            isShowOutCircle = typedArray.getBoolean(
                R.styleable.CircularProgressView_cpv_isShowOutCircle,
                isShowOutCircle
            )
            isShowInnerCircle = typedArray.getBoolean(
                R.styleable.CircularProgressView_cpv_isShowInnerCircle,
                isShowInnerCircle
            )
            isProgressGradient = typedArray.getBoolean(
                R.styleable.CircularProgressView_cpv_isProgressGradient,
                isProgressGradient
            )
            mProgressStartColor = typedArray.getInt(
                R.styleable.CircularProgressView_cpv_progressStart,
                mProgressStartColor
            )
            mProgressEndColor = typedArray.getInt(
                R.styleable.CircularProgressView_cpv_progressEnd,
                mProgressEndColor
            )
        } finally {
            typedArray.recycle()
        }

        // Init Background
        mBackgroundPaint = Paint(Paint.ANTI_ALIAS_FLAG)
        mBackgroundPaint!!.color = mBackgroundColor
        mBackgroundPaint!!.style = Paint.Style.STROKE
        mBackgroundPaint!!.strokeWidth = backgroundStrokeWidth

        // Init Circle
        mCirclePaint = Paint(Paint.ANTI_ALIAS_FLAG)
        mCirclePaint!!.color = mCircleColor
        mCirclePaint!!.style = Paint.Style.STROKE
        mCirclePaint!!.strokeWidth = mCircleWidth

        // Init InnerCircle
        mInnerCirclePaint = Paint()
        mInnerCirclePaint!!.color = mInnerCircleColor
        mInnerCirclePaint!!.isAntiAlias = true

        // Init OutsideRing
        mOutsideRingPaint = Paint(Paint.ANTI_ALIAS_FLAG)
        mOutsideRingPaint!!.color = mOutsideRingColor
        mOutsideRingPaint!!.style = Paint.Style.STROKE
        mOutsideRingPaint!!.strokeWidth = mOutsideRingWidth

        // Init TextView
        mTextView = TextView(context)
        mTextView.visibility = VISIBLE
        mTextView.gravity = Gravity.CENTER_HORIZONTAL
        mTextView.textSize = mTextSize.toFloat()
        mTextView.setTextColor(mTextColor)

        // Init TipTextView
        mTipTextView = TextView(context)
        mTipTextView.visibility = VISIBLE
        mTipTextView.gravity = Gravity.CENTER_HORIZONTAL
        mTipTextView.textSize = mTipTextSize.toFloat()
        mTipTextView.setTextColor(mTipTextColor)

        // Init Layout
        mLayout = LinearLayout(context)
        mLayout.orientation = LinearLayout.VERTICAL
        mLayout.gravity = Gravity.CENTER
        mLayout.addView(mTextView)
        mLayout.addView(mTipTextView)
        showTextView(mIsTextEnabled)
    }

    private fun showTextView(mIsTextEnabled: Boolean) {
        mTextView.text = textPrefix +
                (mProgress / mMaxProgress * 100).roundToInt() +
                textSuffix
        mTextView.visibility = if (mIsTextEnabled) VISIBLE else GONE
        mTextView.paint.isFakeBoldText = mTextBold
        mTipTextView.text = mTipText
        mTipTextView.visibility = if (mIsTextEnabled && mTipTextView.text.toString().isNotEmpty()
        ) VISIBLE else GONE
        invalidate()
    }

    /**
     * 设置默认状态
     */
    private fun setDefaultValues() {
        mProgress = 0f
        mMaxProgress = 100f
        mCircleWidth = resources.getDimension(R.dimen.default_circle_width)
        mOutsideRingWidth = resources.getDimension(R.dimen.default_outside_ring_width)
        backgroundStrokeWidth = resources.getDimension(R.dimen.default_circle_background_width)
        mCircleColor = Color.BLACK
        mTextColor = Color.BLACK
        mBackgroundColor = Color.GRAY
        mInnerCircleColor = Color.TRANSPARENT
        mOutsideRingColor = Color.WHITE
        mFirstFromSecond = 2
        startAngle = -90f
        mIsTextEnabled = true
        mTextSize = 20
        mTipText = ""
        mTipTextColor = Color.BLACK
        mTipTextSize = 20
        isShowInnerCircle = true
        isShowOutCircle = true
        isProgressGradient = false
        mProgressStartColor = Color.GREEN
        mProgressEndColor = Color.GREEN
    }

    var angle = 0f

    /**
     * 绘画圆、圆弧、文本
     *
     * @param canvas
     */
    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        //Draw OutsideRing
        if (isShowOutCircle) {
            canvas.drawOval(mFirstRectF!!, mOutsideRingPaint!!)
        }
        // Draw Background Circle
        canvas.drawOval(mSecondRectF!!, mBackgroundPaint!!)

        // Draw Circle
        angle = 360 * mProgress / mMaxProgress
        if (isProgressGradient) {
            var i = 0
            while (i < angle) {
                if (i < 180) {
                    mCirclePaint!!.color = (argbEvaluator.evaluate(
                        i / 360f,
                        mProgressStartColor,
                        mProgressEndColor
                    ) as Int)
                } else {
                    mCirclePaint!!.color = (argbEvaluator.evaluate(
                        i / 360f,
                        mProgressEndColor,
                        mProgressStartColor
                    ) as Int)
                }
                canvas.drawArc(
                    mSecondRectF!!,
                    startAngle + i,
                    1.5f,
                    false,
                    mCirclePaint!!
                ) // 绘制圆弧 1.35f是每个色块宽度
                i++
            }
        } else {
            canvas.drawArc(mSecondRectF!!, startAngle, angle, false, mCirclePaint!!)
        }

        //Draw innerCircle
        if (isShowInnerCircle) {
            canvas.drawCircle(innerCircleX, innerCircleY, innerCircleRadius, mInnerCirclePaint!!)
        }
        // Draw TextView
        mLayout.measure(width, height)
        mLayout.layout(0, 0, width, height)
        canvas.translate(0f, 0f)
        mLayout.draw(canvas)
    }

    /**
     * 测量宽高
     *
     * @param widthMeasureSpec
     * @param heightMeasureSpec
     */
    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val height = getDefaultSize(suggestedMinimumHeight, heightMeasureSpec)
        val width = getDefaultSize(suggestedMinimumWidth, widthMeasureSpec)
        val min = Math.min(width, height)
        setMeasuredDimension(min, min)
        val stroke =
            if (mCircleWidth > backgroundStrokeWidth) mCircleWidth else backgroundStrokeWidth
        mFirstRectF!![mOutsideRingWidth, mOutsideRingWidth, min - mOutsideRingWidth] =
            min - mOutsideRingWidth
        mSecondRectF!![mFirstFromSecond + mOutsideRingWidth + stroke / 2, mFirstFromSecond + mOutsideRingWidth + stroke / 2, min - (mFirstFromSecond + mOutsideRingWidth + stroke / 2)] =
            min - (mFirstFromSecond + mOutsideRingWidth + stroke / 2)
        innerCircleX = Math.abs(width / 2).toFloat()
        innerCircleY = Math.abs(height / 2).toFloat()
        innerCircleRadius = Math.abs(
            min / 2 - (Math.max(
                mCircleWidth,
                backgroundStrokeWidth
            ) + mFirstFromSecond + mOutsideRingWidth)
        )
    }

    /**
     * 获取是否显示外环线
     *
     * @return
     */
    fun isShowOutCircle(): Boolean {
        return isShowOutCircle
    }

    /**
     * 设置是否显示外环线
     *
     * @param isShowOutCircle
     */
    fun setShowOutCircle(isShowOutCircle: Boolean) {
        this.isShowOutCircle = isShowOutCircle
        invalidate()
    }

    /**
     * 获取是否显示内圆
     *
     * @return
     */
    fun isShowInnerCircle(): Boolean {
        return isShowInnerCircle
    }

    /**
     * 设置是否显示内圆
     *
     * @param isShowInnerCircle
     */
    fun setShowInnerCircle(isShowInnerCircle: Boolean) {
        this.isShowInnerCircle = isShowInnerCircle
        invalidate()
    }

    /**
     * 获取进度条颜色是否是渐变
     *
     * @return
     */
    fun isProgressGradient(): Boolean {
        return isProgressGradient
    }

    /**
     * 设置进度条颜色是否渐变
     * 渐变的范围是0-180,180-360
     *
     * @param isProgressGradient
     */
    fun setProgressGradient(isProgressGradient: Boolean) {
        this.isProgressGradient = isProgressGradient
        invalidate()
    }
    /**
     * 获取进度条开始的渐变颜色
     *
     * @return
     */
    /**
     * 设置进度条开始的渐变颜色
     *
     */
    var progressStartColor: Int
        get() = mProgressStartColor
        set(progressStartColor) {
            mProgressStartColor = progressStartColor
            invalidate()
        }
    /**
     * 获取进度条结束的渐变颜色
     *
     * @return
     */
    /**
     * 设置进度条结束的渐变颜色
     *
     */
    var progressEndColor: Int
        get() = mProgressEndColor
        set(progressEndColor) {
            mProgressEndColor = progressEndColor
            invalidate()
        }
    /**
     * 获取外环的宽度
     *
     * @return
     */
    /**
     * 设置外环的宽度
     *
     */
    var outsideRingWidth: Float
        get() = mOutsideRingWidth
        set(OutsideRingWidth) {
            mOutsideRingWidth = OutsideRingWidth
            mOutsideRingPaint!!.strokeWidth = OutsideRingWidth
            requestLayout()
            invalidate()
        }
    /**
     * 获取外环的颜色
     *
     * @return
     */
    /**
     * 设置外环颜色
     *
     */
    var outsideRingColor: Int
        get() = mOutsideRingColor
        set(OutsideRingColor) {
            mOutsideRingColor = OutsideRingColor
            mOutsideRingPaint!!.color = OutsideRingColor
            invalidate()
        }
    /**
     * 获取外环到内环的距离
     *
     * @return
     */
    /**
     * 设置外环到内环的距离
     *
     */
    var firstFromSecond: Int
        get() = mFirstFromSecond
        set(firstFromSecond) {
            mFirstFromSecond = firstFromSecond
            invalidate()
        }
    /**
     * 获取圆弧的宽度
     *
     * @return
     */
    /**
     * 设置圆弧的宽度
     *
     */
    var circleWidth: Float
        get() = mCircleWidth
        set(circleWidth) {
            mCircleWidth = circleWidth
            mCirclePaint!!.strokeWidth = circleWidth
            requestLayout()
            invalidate()
        }

    /**
     * 设置内圆的颜色
     *
     */
    var innerCircleColor: Int
        get() = mInnerCircleColor
        set(innerCircleColor) {
            mInnerCircleColor = innerCircleColor
            mInnerCirclePaint!!.color = innerCircleColor
            invalidate()
        }

    /**
     * 设置圆弧的颜色
     *
     */
    var circleColor: Int
        get() = mCircleColor
        set(circleColor) {
            mCircleColor = circleColor
            mCirclePaint!!.color = circleColor
            invalidate()
        }

    /**
     * 设置文本的前缀内容
     *
     */
    var textPrefix: String?
        get() = if (!TextUtils.isEmpty(mTextPrefix)) mTextPrefix else ""
        set(textPrefix) {
            mTextPrefix = textPrefix
            showTextView(mIsTextEnabled)
        }
    /**
     * 设置文本的后缀内容
     *
     */
    var textSuffix: String?
        get() = if (!TextUtils.isEmpty(mTextSuffix)) mTextSuffix else ""
        set(textSuffix) {
            mTextSuffix = textSuffix
            showTextView(mIsTextEnabled)
        }
    /**
     * 设置当前进度值
     *
     */
    var progress: Float
        get() = mProgress
        set(progress) {
            mProgress = if (progress <= mMaxProgress) progress else mMaxProgress
            mTextView.text = textPrefix + (mProgress / mMaxProgress * 100).roundToInt() + textSuffix
            showTextView(mIsTextEnabled)
            invalidate()
            if (progressAnimationListener != null) {
                progressAnimationListener!!.onValueChanged(progress)
            }
        }
    /**
     * 设置文字的大小
     *
     */
    var textSize: Int
        get() = mTextSize
        set(textSize) {
            mTextSize = textSize
            mTextView.textSize = textSize.toFloat()
            invalidate()
        }

    /**
     * 设置文字加粗
     *
     * @param textBold
     */
    fun setTextBold(textBold: Boolean) {
        mTextBold = textBold
        mTextView.paint.isFakeBoldText = textBold
        invalidate()
    }
    /**
     * 设置文本可用（显示）状态
     *
     */
    var isTextEnabled: Boolean
        get() = mIsTextEnabled
        set(isTextEnabled) {
            mIsTextEnabled = isTextEnabled
            showTextView(isTextEnabled)
        }

    /**
     * 获取圆弧背景颜色
     *
     * @return
     */
    fun getBackgroundColor(): Int {
        return mBackgroundColor
    }

    /**
     * 设置圆弧背景颜色
     *
     * @param backgroundColor
     */
    override fun setBackgroundColor(backgroundColor: Int) {
        mBackgroundColor = backgroundColor
        mBackgroundPaint!!.color = backgroundColor
        invalidate()
    }

    /**
     * 获取圆弧背景的宽度
     *
     * @return
     */
    fun getBackgroundStrokeWidth(): Float {
        return backgroundStrokeWidth
    }

    /**
     * 设置圆弧背景的宽度
     *
     * @param backgroundStrokeWidth
     */
    fun setBackgroundStrokeWidth(backgroundStrokeWidth: Float) {
        this.backgroundStrokeWidth = backgroundStrokeWidth
        mBackgroundPaint!!.strokeWidth = backgroundStrokeWidth
        requestLayout()
        invalidate()
    }

    /**
     * 设置文本字体的颜色
     *
     */
    var textColor: Int
        get() = mTextColor
        set(textColor) {
            mTextColor = textColor
            mTextView.setTextColor(textColor)
            invalidate()
        }
    /**
     * 设置提示文本字体颜色
     *
     */
    var tipTextColor: Int
        get() = mTipTextColor
        set(tipTextColor) {
            mTipTextColor = tipTextColor
            mTipTextView.setTextColor(tipTextColor)
            invalidate()
        }
    /**
     * 设置提示文本字体大小
     *
     */
    var tipTextSize: Int
        get() = mTipTextSize
        set(tipTextSize) {
            mTipTextSize = tipTextSize
            mTipTextView.textSize = tipTextSize.toFloat()
            invalidate()
        }

    /**
     * 设置提示文本内容
     *
     * @param tipText
     */
    fun setTipText(tipText: String?) {
        mTipText = tipText
        showTextView(mIsTextEnabled)
    }
    /**
     * 设置最大进度值
     *
     */
    var maxProgress: Float
        get() = mMaxProgress
        set(maxProgress) {
            mMaxProgress = maxProgress
            invalidate()
        }

    /**
     * 设置进度动画
     *
     * @param progress 进度值
     * @param duration 动画时间，单位毫秒
     */
    fun setProgressWithAnimation(progress: Float, duration: Int) {
        val objectAnimator = ObjectAnimator.ofFloat(this, "progress", progress)
        objectAnimator.duration = duration.toLong()
        //        objectAnimator.setInterpolator(mInterpolator != null ? mInterpolator : new DecelerateInterpolator());
        objectAnimator.addListener(object : Animator.AnimatorListener {
            override fun onAnimationStart(animation: Animator) {}
            override fun onAnimationEnd(animation: Animator) {
                mProgress = if (progress <= mMaxProgress) progress else mMaxProgress
                if (progressAnimationListener != null) {
                    progressAnimationListener!!.onAnimationEnd()
                }
            }

            override fun onAnimationCancel(animation: Animator) {}
            override fun onAnimationRepeat(animation: Animator) {}
        })
        objectAnimator.addUpdateListener { animation ->
            mTextView.text = textPrefix +
                    (animation.animatedValue as Float / mMaxProgress * 100).roundToInt() +
                    textSuffix
        }
        objectAnimator.start()
        if (progressAnimationListener != null) {
            progressAnimationListener!!.onValueChanged(progress)
        }
    }

    /**
     * 添加动画监听
     *
     * @param progressAnimationListener
     */
    fun addAnimationListener(progressAnimationListener: ProgressAnimationListener?) {
        this.progressAnimationListener = progressAnimationListener
    }

    inner class Interpolator

    /**
     * 动画监听接口
     */
    interface ProgressAnimationListener {
        fun onValueChanged(value: Float)
        fun onAnimationEnd()
    }

    init {
        init(context, attrs)
    }
}