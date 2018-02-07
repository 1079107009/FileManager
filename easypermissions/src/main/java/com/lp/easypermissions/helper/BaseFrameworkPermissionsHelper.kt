package com.lp.easypermissions.helper

import android.app.FragmentManager
import android.support.annotation.NonNull
import android.util.Log
import com.lp.easypermissions.RationaleDialogFragment


/**
 * Created by LiPin on 2017/10/11 10:13.
 * 描述：
 */
abstract class BaseFrameworkPermissionsHelper<out T>(host: T) : PermissionHelper<T>(host) {

    private val TAG = "BFPermissionsHelper"

    abstract fun getFragmentManager(): FragmentManager

    override fun showRequestPermissionRationale(@NonNull rationale: String,
                                                positiveButton: Int,
                                                negativeButton: Int,
                                                requestCode: Int,
                                                @NonNull vararg perms: String) {
        val fm = getFragmentManager()

        // Check if fragment is already showing
        val fragment = fm.findFragmentByTag(RationaleDialogFragment.TAG)
        if (fragment is RationaleDialogFragment) {
            Log.d(TAG, "Found existing fragment, not showing rationale.")
            return
        }
        RationaleDialogFragment
                .newInstance(positiveButton, negativeButton, rationale, requestCode, perms)
                .showAllowingStateLoss(getFragmentManager(), RationaleDialogFragment.TAG)
    }
}