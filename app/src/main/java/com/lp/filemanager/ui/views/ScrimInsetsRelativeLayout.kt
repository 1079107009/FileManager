package com.lp.filemanager.ui.views

import android.content.Context
import android.content.res.TypedArray
import android.graphics.Canvas
import android.graphics.Rect
import android.graphics.drawable.Drawable
import android.support.v4.view.ViewCompat
import android.util.AttributeSet
import android.widget.RelativeLayout
import com.lp.filemanager.R

/**
 * Created by LiPin on 2017/9/20 9:54.
 * 描述：
 */
class ScrimInsetsRelativeLayout : RelativeLayout {

    var mInsetForeground: Drawable? = null
    var mInsets: Rect? = null
    private val mTempRect = Rect()
    var mOnInsetsCallback: OnInsetsCallback? = null

    constructor(context: Context) : super(context) {
        init(context, null, 0)
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init(context, attrs, 0)
    }

    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(context, attrs, defStyle) {
        init(context, attrs, defStyle)
    }

    private fun init(context: Context, attrs: AttributeSet?, defStyle: Int) {
        val a: TypedArray? = context.obtainStyledAttributes(attrs, R.styleable.ScrimInsetsFrameLayout, defStyle, 0)
        mInsetForeground = a?.getDrawable(R.styleable.ScrimInsetsFrameLayout_insetForeground)
        a?.recycle()
        setWillNotDraw(true)
    }

    override fun fitSystemWindows(insets: Rect): Boolean {
        mInsets = Rect(insets)
        //如果mInsetForeground为空不绘制
        setWillNotDraw(mInsetForeground == null)
        ViewCompat.postInvalidateOnAnimation(this)
        mOnInsetsCallback?.onInsetsChanged(insets)
        return true
    }

    override fun draw(canvas: Canvas) {
        super.draw(canvas)
        if (mInsets != null && mInsetForeground != null) {
            val sc = canvas.save()
            canvas.translate(scrollX.toFloat(), scrollY.toFloat())
            // Top
            mTempRect.set(0, 0, width, mInsets!!.top)
            mInsetForeground?.bounds = mTempRect
            mInsetForeground?.draw(canvas)

            // Bottom
            mTempRect.set(0, height - mInsets!!.bottom, width, height)
            mInsetForeground?.bounds = mTempRect
            mInsetForeground?.draw(canvas)

            // Left
            mTempRect.set(0, mInsets!!.top, mInsets!!.left, height - mInsets!!.bottom)
            mInsetForeground?.bounds = mTempRect
            mInsetForeground?.draw(canvas)

            // Right
            mTempRect.set(width - mInsets!!.right, mInsets!!.top, width, height - mInsets!!.bottom)
            mInsetForeground?.bounds = mTempRect
            mInsetForeground?.draw(canvas)

            canvas.restoreToCount(sc)
        }
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        mInsetForeground?.callback = this
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        mInsetForeground?.callback = null
    }

    interface OnInsetsCallback {
        fun onInsetsChanged(insets: Rect)
    }
}

