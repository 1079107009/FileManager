package com.lp.easypermissions.helper

import android.support.annotation.NonNull
import android.support.v4.app.FragmentManager


/**
 * Created by LiPin on 2017/10/10 14:27.
 * 描述：
 */
abstract class BaseSupportPermissionsHelper<out T>(host: T) : PermissionHelper<T>(host) {
    abstract fun getSupportFragmentManager(): FragmentManager

    override fun showRequestPermissionRationale(@NonNull rationale: String,
                                                positiveButton: Int,
                                                negativeButton: Int,
                                                requestCode: Int,
                                                @NonNull vararg perms: String) {
        RationaleDialogFragmentCompat
                .newInstance(positiveButton, negativeButton, rationale, requestCode, perms)
                .showAllowingStateLoss(getSupportFragmentManager(), RationaleDialogFragmentCompat.TAG)
    }
}