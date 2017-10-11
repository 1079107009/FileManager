package com.lp.easypermissions.helper

import android.content.Context
import android.support.v4.app.ActivityCompat
import android.support.v4.app.FragmentManager
import android.support.v7.app.AppCompatActivity

/**
 * Created by LiPin on 2017/10/10 14:25.
 * 描述：Permissions helper for {@link AppCompatActivity}.
 */
class AppCompatActivityPermissionHelper(host: AppCompatActivity) :
        BaseSupportPermissionsHelper<AppCompatActivity>(host) {

    override fun getSupportFragmentManager(): FragmentManager = getHost().supportFragmentManager

    override fun directRequestPermissions(requestCode: Int, vararg perms: String) {
        ActivityCompat.requestPermissions(getHost(), perms, requestCode)
    }

    override fun shouldShowRequestPermissionRationale(perm: String): Boolean =
            ActivityCompat.shouldShowRequestPermissionRationale(getHost(), perm)

    override fun getContext(): Context? = getHost()
}