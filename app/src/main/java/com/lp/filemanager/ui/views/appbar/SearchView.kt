package com.lp.filemanager.ui.views.appbar

import android.animation.Animator
import android.animation.ObjectAnimator
import android.os.Build.VERSION.SDK_INT
import android.support.constraint.ConstraintLayout
import android.support.v7.widget.AppCompatEditText
import android.view.View
import android.view.ViewAnimationUtils
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.inputmethod.EditorInfo
import android.widget.ImageView
import android.widget.TextView
import com.lp.filemanager.R
import com.lp.filemanager.activities.MainActivity
import com.lp.filemanager.utils.KeyBoardUtils
import kotlinx.android.synthetic.main.layout_search.*
import java.lang.ref.WeakReference

/**
 * Created by LiPin on 2017/9/20 15:04.
 * 描述：
 */
class SearchView(appbar: AppBar, a: MainActivity, searchListener: SearchListener) {

    private var mainActivity: WeakReference<MainActivity>? = null
    private var appbar: AppBar? = null

    private var searchViewLayout: ConstraintLayout? = null
    private var searchViewEditText: AppCompatEditText? = null
    private var clearImageView: ImageView? = null
    private var backImageView: ImageView? = null

    private var enabled = false

    init {
        mainActivity = WeakReference(a)
        this.appbar = appbar
        searchViewLayout = a.search_view
        searchViewEditText = a.search_edit_text
        clearImageView = a.search_close_btn
        backImageView = a.img_view_back

        clearImageView?.setOnClickListener { searchViewEditText?.setText("") }
        backImageView?.setOnClickListener {
            hideSearchView()
        }
        searchViewEditText?.setOnEditorActionListener(TextView.OnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                searchListener.onSearch(searchViewEditText?.text.toString())
                hideSearchView()
                return@OnEditorActionListener true
            }
            false
        })
    }

    /**
     * show search view with a circular reveal animation
     */
    public fun showSearchView() {
        val START_RADIUS = 16
        val endRadius = Math.max(appbar!!.getToolbar().width, appbar!!.getToolbar().height)
        val animator: Animator = if (SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            val searchCoords: IntArray? = null
            val searchItem = appbar!!.getToolbar().findViewById(R.id.search)
            searchViewEditText!!.setText("")
            searchItem.getLocationInWindow(searchCoords)
            ViewAnimationUtils.createCircularReveal(searchViewLayout,
                    searchCoords!![0] + 32, searchCoords[1] - 16, START_RADIUS.toFloat(), endRadius.toFloat())
        } else {
            ObjectAnimator.ofFloat(searchViewLayout, "alpha", 0f, 1f)
        }

        mainActivity?.get()?.showSmokeScreen()
        animator.interpolator = AccelerateDecelerateInterpolator()
        animator.duration = 600
        searchViewLayout!!.visibility = View.VISIBLE
        animator.addListener(object : Animator.AnimatorListener {
            override fun onAnimationRepeat(animation: Animator?) {
            }

            override fun onAnimationEnd(animation: Animator?) {
                enabled = true
                searchViewEditText!!.requestFocus()
                KeyBoardUtils.openKeyboard(mainActivity?.get()!!.applicationContext, searchViewEditText!!)
            }

            override fun onAnimationCancel(animation: Animator?) {
            }

            override fun onAnimationStart(animation: Animator?) {
            }

        })
        animator.start()
    }

    /**
     * hide search view with a circular reveal animation
     */
    private fun hideSearchView() {
        val END_RADIUS = 16
        val startRadius = Math.max(searchViewLayout!!.width, searchViewLayout!!.height)
        val animator: Animator = if (SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            val searchCoords: IntArray? = null
            val searchItem = appbar!!.getToolbar().findViewById(R.id.search)
            searchViewEditText!!.setText("")
            searchItem.getLocationInWindow(searchCoords)
            ViewAnimationUtils.createCircularReveal(searchViewLayout,
                    searchCoords!![0] + 32, searchCoords[1] - 16, startRadius.toFloat(), END_RADIUS.toFloat())
        } else {
            ObjectAnimator.ofFloat(searchViewLayout, "alpha", 1f, 0f)
        }

        mainActivity?.get()?.hideSmokeScreen()
        animator.interpolator = AccelerateDecelerateInterpolator()
        animator.duration = 600
        animator.addListener(object : Animator.AnimatorListener {
            override fun onAnimationRepeat(animation: Animator?) {
            }

            override fun onAnimationEnd(animation: Animator?) {
                enabled = false
                searchViewLayout!!.visibility = View.GONE
                KeyBoardUtils.closeKeyboard(mainActivity?.get()!!.applicationContext, searchViewEditText!!)
            }

            override fun onAnimationCancel(animation: Animator?) {
            }

            override fun onAnimationStart(animation: Animator?) {
            }

        })
        animator.start()
    }

    fun isEnabled() = enabled

    fun isShown() = searchViewLayout?.isShown ?: false

    interface SearchListener {
        fun onSearch(queue: String)
    }
}