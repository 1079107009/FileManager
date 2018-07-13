package com.lp.filemanager.application

import android.app.Application
import com.squareup.leakcanary.LeakCanary

/**
 * Created by LiPin on 2017/9/19 13:52.
 * 描述：
 */
class FileApplication : Application() {

    private val TAG = "FileApplication"

    override fun onCreate() {
        super.onCreate()
        if (LeakCanary.isInAnalyzerProcess(this)) {
            //这个进程是专门LeakCanary用于堆分析的，不应该在这个进程初始化app
            return
        }
        LeakCanary.install(this)
    }
}