package com.lp.easypermissions.helper

import android.app.Activity
import android.content.Context
import android.support.v4.app.Fragment


/**
 * Created by LiPin on 2017/10/10 13:14.
 * 描述：低于6.0不需要运行时权限
 */
class LowApiPermissionsHelper(host: Any) : PermissionHelper<Any>(host) {

    override fun directRequestPermissions(requestCode: Int, vararg perms: String) {
        throw IllegalStateException("Should never be requesting permissions on API < 23!")
    }

    override fun shouldShowRequestPermissionRationale(perm: String) = false

    override fun showRequestPermissionRationale(rationale: String,
                                                positiveButton: Int,
                                                negativeButton: Int,
                                                requestCode: Int,
                                                vararg perms: String) {
        throw IllegalStateException("Should never be requesting permissions on API < 23!")
    }

    override fun getContext(): Context? = when {
        getHost() is Activity -> getHost() as Context
        getHost() is Fragment -> (getHost() as Fragment).context
        else -> throw IllegalStateException("Unknown host: " + getHost())
    }
}