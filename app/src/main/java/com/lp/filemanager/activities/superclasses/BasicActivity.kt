package com.lp.filemanager.activities.superclasses

import android.support.v7.app.AppCompatActivity
import com.lp.filemanager.application.FileApplication

/**
 * Created by LiPin on 2017/9/21 15:05.
 * 描述：
 */
open class BasicActivity : AppCompatActivity() {


    fun getAppConfig(): FileApplication = application as FileApplication
}