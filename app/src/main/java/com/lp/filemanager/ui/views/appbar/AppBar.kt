package com.lp.filemanager.ui.views.appbar

import android.content.SharedPreferences
import android.support.design.widget.AppBarLayout
import android.support.v7.widget.Toolbar
import com.lp.filemanager.activities.MainActivity
import kotlinx.android.synthetic.main.layout_appbar.*

/**
 * Created by LiPin on 2017/9/20 14:02.
 * 描述：
 */
class AppBar {

    private var toolbar: Toolbar? = null
    private var searchView: SearchView? = null
    private var bottomBar: BottomBar? = null

    private var appbarLayout: AppBarLayout? = null

    constructor(a: MainActivity, sharedPref: SharedPreferences, searchListener: SearchView.SearchListener) {
        toolbar = a.action_bar
        searchView = SearchView(this, a, searchListener)
        bottomBar = BottomBar(this, a)
        appbarLayout = a.appbar

        if (!sharedPref.getBoolean("intelliHideToolbar", true)) {
            val params = toolbar!!.layoutParams as AppBarLayout.LayoutParams
            params.scrollFlags = 0
            appbarLayout!!.setExpanded(true, true)
        }
    }

    fun getToolbar(): Toolbar = toolbar!!

    fun getSearchView(): SearchView = searchView!!

    fun getBottomBar(): BottomBar = bottomBar!!

    fun getAppbarLayout(): AppBarLayout = appbarLayout!!

    fun setTitle(title: String) {
        toolbar?.title = title
    }
}