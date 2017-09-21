package com.lp.filemanager.activities.superclasses

import android.content.SharedPreferences
import android.os.Bundle
import android.preference.PreferenceManager

/**
 * Created by LiPin on 2017/9/21 15:10.
 * 描述：
 */
open class PreferenceActivity : BasicActivity() {

    private var sharedPrefs: SharedPreferences? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this)
    }

    public fun getPrefs() = sharedPrefs!!

}