package com.lp.easypermissions.helper

import android.support.annotation.NonNull
import android.support.v4.app.FragmentManager
import android.util.Log
import com.lp.easypermissions.RationaleDialogFragmentCompat


/**
 * Created by LiPin on 2017/10/10 14:27.
 * 描述：
 */
abstract class BaseSupportPermissionsHelper<out T>(host: T) : PermissionHelper<T>(host) {

    private val TAG = "BSPermissionsHelper"

    abstract fun getSupportFragmentManager(): FragmentManager

    override fun showRequestPermissionRationale(@NonNull rationale: String,
                                                positiveButton: Int,
                                                negativeButton: Int,
                                                requestCode: Int,
                                                @NonNull vararg perms: String) {
        val fm = getSupportFragmentManager()

        // Check if fragment is already showing
        val fragment = fm.findFragmentByTag(RationaleDialogFragmentCompat.TAG)
        if (fragment is RationaleDialogFragmentCompat) {
            Log.d(TAG, "Found existing fragment, not showing rationale.")
            return
        }
        RationaleDialogFragmentCompat
                .newInstance(positiveButton, negativeButton, rationale, requestCode, *perms)
                .showAllowingStateLoss(getSupportFragmentManager(), RationaleDialogFragmentCompat.TAG)
    }
}