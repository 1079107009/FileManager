package com.lp.filemanager.activities

import android.os.Bundle
import android.support.design.widget.AppBarLayout
import android.view.Menu
import com.lp.filemanager.R
import com.lp.filemanager.activities.superclasses.ThemedActivity
import com.lp.filemanager.ui.views.appbar.AppBar
import com.lp.filemanager.ui.views.appbar.SearchView

class MainActivity : ThemedActivity() {

    private var appbar: AppBar? = null
    private var appBarLayout: AppBarLayout? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initView()
    }

    private fun initView() {
        appbar = AppBar(this, getPrefs(), object : SearchView.SearchListener {
            override fun onSearch(queue: String) {

            }

        })
    }

    fun hideSmokeScreen() {

    }

    fun showSmokeScreen() {
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.activity_extra, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onPrepareOptionsMenu(menu: Menu?): Boolean {

        return super.onPrepareOptionsMenu(menu)
    }
}
