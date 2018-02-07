package com.lp.easypermissions.helper

import android.app.Activity
import android.content.Context
import android.os.Build
import android.support.annotation.NonNull
import android.support.annotation.StringRes
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity


/**
 * Created by LiPin on 2017/10/10 9:50.
 * 描述：代理类
 */
abstract class PermissionHelper<out T>(@NonNull host: T) {

    private val TAG = "PermissionHelper"

    private val mHost: T = host

    companion object {

        fun newInstance(host: Activity): PermissionHelper<Any> {
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
                return LowApiPermissionsHelper(host)
            }

            return if (host is AppCompatActivity) {
                AppCompatActivityPermissionHelper(host)
            } else {
                ActivityPermissionHelper(host)
            }
        }

        fun newInstance(host: Fragment): PermissionHelper<Any> {
            return if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
                LowApiPermissionsHelper(host)
            } else SupportFragmentPermissionHelper(host)

        }

    }

    private fun shouldShowRationale(@NonNull vararg perms: String): Boolean =
            perms.any { shouldShowRequestPermissionRationale(it) }

    fun requestPermissions(@NonNull rationale: String,
                           @StringRes positiveButton: Int,
                           @StringRes negativeButton: Int,
                           requestCode: Int,
                           @NonNull vararg perms: String) {
        if (shouldShowRationale(*perms)) {
            showRequestPermissionRationale(
                    rationale, positiveButton, negativeButton, requestCode, *perms)
        } else {
            directRequestPermissions(requestCode, *perms)
        }
    }

    fun somePermissionPermanentlyDenied(@NonNull vararg perms: String): Boolean =
            //如果元素中至少有一个满足则返回true
            perms.any { permissionPermanentlyDenied(it) }

    fun permissionPermanentlyDenied(@NonNull perms: String): Boolean =
            !shouldShowRequestPermissionRationale(perms)

    fun somePermissionDenied(@NonNull vararg perms: String): Boolean = shouldShowRationale(*perms)

    @NonNull
    fun getHost(): T = mHost

    // ============================================================================
    // Public abstract methods
    // ============================================================================

    abstract fun directRequestPermissions(requestCode: Int, @NonNull vararg perms: String)

    abstract fun shouldShowRequestPermissionRationale(@NonNull perm: String): Boolean

    abstract fun showRequestPermissionRationale(@NonNull rationale: String,
                                                @StringRes positiveButton: Int,
                                                @StringRes negativeButton: Int,
                                                requestCode: Int,
                                                @NonNull vararg perms: String)

    abstract fun getContext(): Context?

}