package com.lp.filemanager.activities.superclasses

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.lp.filemanager.application.FileApplication

/**
 * Created by LiPin on 2017/9/21 15:05.
 * 描述：
 */
abstract class BasicActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getLayoutId())
        createViewModelAndObserveLiveData()
        init()
    }

    fun getAppConfig(): FileApplication = application as FileApplication

    /**
     * 提供layoutId
     */
    abstract fun getLayoutId(): Int

    /**
     * inflate view完成之后，初始化ViewModel,并监听所需要的liveData
     */
    abstract fun createViewModelAndObserveLiveData()

    /**
     * 在构造好ViewModel之后进行的初始化动作
     */
    abstract fun init()
}