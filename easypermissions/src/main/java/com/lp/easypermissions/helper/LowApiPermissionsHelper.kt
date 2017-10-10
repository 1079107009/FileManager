package com.lp.easypermissions.helper

import android.content.Context

/**
 * Created by LiPin on 2017/10/10 13:14.
 * 描述：
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

    override fun getContext(): Context? = null
}