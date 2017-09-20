package com.lp.filemanager.activities

import android.os.Bundle
import android.support.design.widget.AppBarLayout
import android.support.v7.app.AppCompatActivity
import com.lp.filemanager.R

class MainActivity : AppCompatActivity() {

    private var appBarLayout: AppBarLayout? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initView()
    }

    private fun initView() {
    }

    fun hideSmokeScreen() {

    }

    fun showSmokeScreen() {
    }

}
