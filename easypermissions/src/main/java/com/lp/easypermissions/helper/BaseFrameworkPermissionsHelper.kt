package com.lp.easypermissions.helper

import android.app.FragmentManager
import android.support.annotation.NonNull
import com.lp.easypermissions.RationaleDialogFragment


/**
 * Created by LiPin on 2017/10/11 10:13.
 * 描述：
 */
abstract class BaseFrameworkPermissionsHelper<out T>(host: T) : PermissionHelper<T>(host) {

    abstract fun getFragmentManager(): FragmentManager

    override fun showRequestPermissionRationale(@NonNull rationale: String,
                                                positiveButton: Int,
                                                negativeButton: Int,
                                                requestCode: Int,
                                                @NonNull vararg perms: String) {
        RationaleDialogFragment
                .newInstance(positiveButton, negativeButton, rationale, requestCode, perms)
                .showAllowingStateLoss(getFragmentManager(), RationaleDialogFragment.TAG)
    }
}