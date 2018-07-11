package com.lp.filemanager.activities

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import com.lp.filemanager.R
import com.lp.filemanager.activities.superclasses.ThemedActivity
import kotlinx.android.synthetic.main.layout_appbar.*
import kotlinx.android.synthetic.main.layout_search.*


class MainActivity : ThemedActivity() {

    private val TAG = "MainActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "setContentView")
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        setListener()
    }

    private fun setListener() {
        searchView.setListener()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_search -> {
                searchView.show()
            }
            R.id.menu_history -> {
            }
            R.id.menu_setting -> {
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onBackPressed() {
        if (searchView.isShowing()) {
            searchView.hide()
        } else {
            super.onBackPressed()
        }
    }
}
