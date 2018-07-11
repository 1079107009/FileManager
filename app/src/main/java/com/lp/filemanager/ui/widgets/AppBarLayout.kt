package com.lp.filemanager.ui.widgets

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.widget.FrameLayout

/**
 *
 * @author lipin
 * @date 2018/7/11
 */
class AppBarLayout : FrameLayout {

    private val TAG = "AppBarLayout"

    constructor(context: Context) : this(context, null)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init()
    }

    private fun init() {
        Log.d(TAG, "init")
    }
}