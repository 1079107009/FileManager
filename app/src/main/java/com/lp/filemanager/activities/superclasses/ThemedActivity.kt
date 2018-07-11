package com.lp.filemanager.activities.superclasses

import android.os.Build
import android.os.Build.VERSION.SDK_INT
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.view.WindowManager
import com.lp.filemanager.R
import com.readystatesoftware.systembartint.SystemBarTintManager


/**
 * Created by LiPin on 2017/9/21 15:15.
 * 描述：
 */
open class ThemedActivity : PreferenceActivity() {

    private val TAG = "ThemedActivity"

    companion object {
        var rootMode = false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStatusBarColor()
        //请求存储权限
//        val rxPermissions = RxPermissions(this)
//        rxPermissions
//                .request(Manifest.permission.READ_EXTERNAL_STORAGE)
//                .subscribe {
//                    if (!it) {
//                        GeneralDialogCreation.showBasicDialog(this, with(resources) {
//                            val texts = arrayOf(
//                                    getString(R.string.grant_text),
//                                    getString(R.string.prompt),
//                                    getString(R.string.grant),
//                                    getString(R.string.cancel))
//                            texts
//                        }).show()
//                    }
//                }
    }

    private fun setStatusBarColor() {
        val color = ContextCompat.getColor(this, R.color.colorAccent)
        if (SDK_INT == Build.VERSION_CODES.KITKAT_WATCH || SDK_INT == Build.VERSION_CODES.KITKAT) {
            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            val tintManager = SystemBarTintManager(this)
            tintManager.isStatusBarTintEnabled = true
            tintManager.setStatusBarTintColor(color)
        } else if (SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            window.statusBarColor = color
        }
    }

}
