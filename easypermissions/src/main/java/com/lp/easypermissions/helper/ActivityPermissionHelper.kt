package com.lp.easypermissions.helper

import android.app.Activity
import android.app.FragmentManager
import android.content.Context
import android.support.v4.app.ActivityCompat

/**
 * Created by LiPin on 2017/10/11 10:09.
 * 描述：
 */
class ActivityPermissionHelper(host: Activity) : BaseFrameworkPermissionsHelper<Activity>(host) {

    override fun getFragmentManager(): FragmentManager = getHost().fragmentManager

    override fun directRequestPermissions(requestCode: Int, vararg perms: String) {
        ActivityCompat.requestPermissions(getHost(), perms, requestCode)
    }

    override fun shouldShowRequestPermissionRationale(perm: String): Boolean =
            ActivityCompat.shouldShowRequestPermissionRationale(getHost(), perm)

    override fun getContext(): Context? = getHost()
}