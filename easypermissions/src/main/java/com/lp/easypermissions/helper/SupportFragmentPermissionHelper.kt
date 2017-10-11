package com.lp.easypermissions.helper

import android.content.Context
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager

/**
 * Created by LiPin on 2017/10/11 10:20.
 * 描述：
 */
class SupportFragmentPermissionHelper(host: Fragment) : BaseSupportPermissionsHelper<Fragment>(host) {

    override fun getSupportFragmentManager(): FragmentManager = getHost().childFragmentManager

    override fun directRequestPermissions(requestCode: Int, vararg perms: String) {
        getHost().requestPermissions(perms, requestCode)
    }

    override fun shouldShowRequestPermissionRationale(perm: String): Boolean =
            getHost().shouldShowRequestPermissionRationale(perm)

    override fun getContext(): Context? = getHost().activity
}