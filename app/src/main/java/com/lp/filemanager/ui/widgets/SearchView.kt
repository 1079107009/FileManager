package com.lp.filemanager.ui.widgets

import android.animation.Animator
import android.animation.ObjectAnimator
import android.app.Activity
import android.content.Context
import android.os.Build.VERSION.SDK_INT
import android.support.v7.widget.Toolbar
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.view.ViewAnimationUtils
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.inputmethod.InputMethodManager
import android.widget.RelativeLayout
import androidx.core.animation.doOnEnd
import com.lp.filemanager.R
import kotlinx.android.synthetic.main.layout_search.view.*

/**
 *
 * @author lipin
 * @date 2018/7/11
 */
class SearchView : RelativeLayout {

    private val TAG = "SearchView"

    constructor(context: Context) : this(context, null)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init()
    }

    private fun init() {
        Log.d(TAG, "init")
    }

    fun isShowing(): Boolean = visibility == View.VISIBLE

    fun show() {
        val startRadius = 16f
        val endRadius = resources.displayMetrics.widthPixels
        val animator: Animator = if (SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            val location = IntArray(2)
            val searchItem = (context as Activity)
                    .findViewById<Toolbar>(R.id.toolbar)
                    .findViewById<View>(R.id.menu_search)
            searchItem.getLocationOnScreen(location)
            ViewAnimationUtils.createCircularReveal(this,
                    location[0] + 32, location[1] - 16, startRadius, endRadius.toFloat())
        } else {
            ObjectAnimator.ofFloat(this, "alpha", 0f, 1f)
        }
        visibility = View.VISIBLE
        animator.interpolator = AccelerateDecelerateInterpolator()
        animator.duration = 600
        animator.start()
        animator.doOnEnd {
            search_edit_text.requestFocus()
            val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.showSoftInput(search_edit_text, InputMethodManager.SHOW_IMPLICIT)
        }
    }

    fun hide() {
        val startRadius = resources.displayMetrics.widthPixels
        val endRadius = 16f
        val animator: Animator = if (SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            val location = IntArray(2)
            val searchItem = (context as Activity)
                    .findViewById<Toolbar>(R.id.toolbar)
                    .findViewById<View>(R.id.menu_search)
            searchItem.getLocationOnScreen(location)
            ViewAnimationUtils.createCircularReveal(this,
                    location[0] + 32, location[1] - 16, startRadius.toFloat(), endRadius)
        } else {
            ObjectAnimator.ofFloat(this, "alpha", 0f, 1f)
        }

        animator.interpolator = AccelerateDecelerateInterpolator()
        animator.duration = 600
        animator.start()
        animator.doOnEnd {
            visibility = View.GONE
            val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(search_edit_text.windowToken, InputMethodManager.HIDE_IMPLICIT_ONLY)
        }
    }

    fun setListener() {
        img_view_back.setOnClickListener {
            hide()
        }
        search_close_btn.setOnClickListener {
            search_edit_text.text = null
        }
    }
}