package com.lp.easypermissions

import android.app.Activity
import android.content.DialogInterface
import android.os.Build
import android.support.v4.app.Fragment
import com.lp.easypermissions.helper.PermissionHelper


/**
 * Created by LiPin on 2017/10/10 15:24.
 * 描述：
 */
class RationaleDialogClickListener : DialogInterface.OnClickListener {

    private var mHost: Any? = null
    private var mConfig: RationaleDialogConfig? = null
    private var mCallbacks: PermissionCallbacks? = null

    constructor(compatDialogFragment: RationaleDialogFragmentCompat,
                config: RationaleDialogConfig,
                callbacks: PermissionCallbacks?) {
        mHost = compatDialogFragment.parentFragment ?: compatDialogFragment.activity
        mConfig = config
        mCallbacks = callbacks
    }

    constructor(dialogFragment: RationaleDialogFragment,
                config: RationaleDialogConfig,
                callbacks: PermissionCallbacks?) {

        mHost = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            dialogFragment.parentFragment ?: dialogFragment.activity
        } else {
            dialogFragment.activity
        }

        mConfig = config
        mCallbacks = callbacks
    }

    override fun onClick(dialog: DialogInterface?, which: Int) {
        if (which == DialogInterface.BUTTON_POSITIVE) {
            when (mHost) {
                is Fragment -> PermissionHelper.newInstance(mHost as Fragment).directRequestPermissions(
                        mConfig!!.requestCode, *mConfig!!.permissions!!)
                is Activity -> PermissionHelper.newInstance(mHost as Activity).directRequestPermissions(
                        mConfig!!.requestCode, *mConfig!!.permissions!!)
                else -> throw RuntimeException("Host must be an Activity or Fragment!")
            }
        } else {
            notifyPermissionDenied()
        }
    }

    private fun notifyPermissionDenied() {
        if (mCallbacks != null) {
            mCallbacks!!.onPermissionsDenied(mConfig!!.requestCode,
                    mConfig?.permissions?.map { it }!!)
        }
    }
}