package com.lp.easypermissions

import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.support.annotation.NonNull
import android.support.annotation.StringRes
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.util.Log
import com.lp.easypermissions.helper.PermissionHelper


/**
 * Created by LiPin on 2017/10/10 9:15.
 * 描述：
 */
object EasyPermissions {

    private val TAG = "EasyPermissions"

    /**
     * Check if the calling context has a set of permissions.
     *
     * @param context the calling context.
     * @param perms   one ore more permissions, such as {@link Manifest.permission#CAMERA}.
     * @return true if all permissions are already granted, false if at least one permission is not
     * yet granted.
     */
    fun hasPermissions(context: Context, @NonNull vararg perms: String): Boolean {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            Log.w(TAG, "hasPermissions: API version < M, returning true by default")
            return true
        }

        return perms.none {
            //检查集合里的权限是否被授予，如果全部授予返回true，否则返回false
            ContextCompat.checkSelfPermission(context, it) != PackageManager.PERMISSION_GRANTED
        }
    }

    /**
     * Request permissions from an Activity with standard OK/Cancel buttons.
     *
     * @see .requestPermissions
     */
    fun requestPermissions(host: Activity, rationale: String,
                           requestCode: Int, @NonNull vararg perms: String) {
        requestPermissions(host, rationale, R.string.ok, R.string.cancel,
                requestCode, *perms)
    }

    /**
     * Request permissions from a Support Fragment with standard OK/Cancel buttons.
     *
     * @see .requestPermissions
     */
    fun requestPermissions(host: Fragment, rationale: String,
                           requestCode: Int, @NonNull vararg perms: String) {
        requestPermissions(host, rationale, R.string.ok, R.string.cancel,
                requestCode, *perms)
    }


    /**
     * Request a set of permissions, showing rationale if the system requests it.
     *
     * @param host           requesting context.
     * @param rationale      a message explaining why the application needs this set of permissions,
     * will be displayed if the user rejects the request the first time.
     * @param positiveButton custom text for positive button
     * @param negativeButton custom text for negative button
     * @param requestCode    request code to track this request, must be &lt; 256.
     * @param perms          a set of permissions to be requested.
     */
    fun requestPermissions(
            host: Activity, rationale: String,
            @StringRes positiveButton: Int, @StringRes negativeButton: Int,
            requestCode: Int, @NonNull vararg perms: String) {
        requestPermissions(PermissionHelper.newInstance(host), rationale,
                positiveButton, negativeButton,
                requestCode, *perms)
    }

    /**
     * Request permissions from a Support Fragment.
     *
     * @see .requestPermissions
     */
    fun requestPermissions(
            host: Fragment, rationale: String,
            @StringRes positiveButton: Int, @StringRes negativeButton: Int,
            requestCode: Int, @NonNull vararg perms: String) {
        requestPermissions(PermissionHelper.newInstance(host), rationale,
                positiveButton, negativeButton,
                requestCode, *perms)
    }

    private fun requestPermissions(
            helper: PermissionHelper, rationale: String,
            @StringRes positiveButton: Int, @StringRes negativeButton: Int,
            requestCode: Int, @NonNull vararg perms: String) {

        // Check for permissions before dispatching the request
        if (hasPermissions(helper.getContext()!!, *perms)) {
            notifyAlreadyHasPermissions(helper.getHost(), requestCode, perms)
            return
        }

        // Request permissions
        helper.requestPermissions(rationale, positiveButton,
                negativeButton, requestCode, *perms)
    }

    /**
     * Handle the result of a permission request, should be called from the calling [ ]'s [ActivityCompat.OnRequestPermissionsResultCallback.onRequestPermissionsResult] method.
     *
     *
     * If any permissions were granted or denied, the `object` will receive the appropriate
     * callbacks through [PermissionCallbacks] and methods annotated with [ ] will be run if appropriate.
     *
     * @param requestCode  requestCode argument to permission result callback.
     * @param permissions  permissions argument to permission result callback.
     * @param grantResults grantResults argument to permission result callback.
     * @param receivers    an array of objects that have a method annotated with [                     ] or implement [PermissionCallbacks].
     */
    fun onRequestPermissionsResult(requestCode: Int,
                                   permissions: Array<out String>,
                                   grantResults: IntArray,
                                   vararg receivers: Any) {
        val granted = ArrayList<String>()
        val denied = ArrayList<String>()
        for (i in permissions.indices) {
            val perm = permissions[i]
            if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                granted.add(perm)
            } else {
                denied.add(perm)
            }
        }

        for (receiver in receivers) {
            if (granted.isNotEmpty()) {
                if (receiver is PermissionCallbacks) {
                    receiver.onPermissionsGranted(requestCode, granted)
                }
            }

            if (denied.isNotEmpty()) {
                if (receiver is PermissionCallbacks) {
                    receiver.onPermissionsDenied(requestCode, denied)
                }
            }

        }
    }

    /**
     * Check if at least one permission in the list of denied permissions has been permanently
     * denied (user clicked "Never ask again").
     *
     * @param host              context requesting permissions.
     * @param deniedPermissions list of denied permissions, usually from [                          ][PermissionCallbacks.onPermissionsDenied]
     * @return `true` if at least one permission in the list was permanently denied.
     */
    fun somePermissionPermanentlyDenied(host: Activity,
                                        vararg deniedPermissions: String): Boolean {
        return PermissionHelper.newInstance(host)
                .somePermissionPermanentlyDenied(*deniedPermissions)
    }

    /**
     * @see .somePermissionPermanentlyDenied
     */
    fun somePermissionPermanentlyDenied(host: Fragment,
                                        vararg deniedPermissions: String): Boolean {
        return PermissionHelper.newInstance(host)
                .somePermissionPermanentlyDenied(*deniedPermissions)
    }

    /**
     * Check if a permission has been permanently denied (user clicked "Never ask again").
     *
     * @param host             context requesting permissions.
     * @param deniedPermission denied permission.
     * @return `true` if the permissions has been permanently denied.
     */
    fun permissionPermanentlyDenied(host: Activity,
                                    deniedPermission: String): Boolean {
        return PermissionHelper.newInstance(host).permissionPermanentlyDenied(deniedPermission)
    }

    /**
     * @see .permissionPermanentlyDenied
     */
    fun permissionPermanentlyDenied(host: Fragment,
                                    deniedPermission: String): Boolean {
        return PermissionHelper.newInstance(host).permissionPermanentlyDenied(deniedPermission)
    }

    /**
     * See if some denied permission has been permanently denied.
     *
     * @param host  requesting context.
     * @param perms array of permissions.
     * @return true if the user has previously denied any of the `perms` and we should show a
     * rationale, false otherwise.
     */
    fun somePermissionDenied(host: Activity,
                             vararg perms: String): Boolean {
        return PermissionHelper.newInstance(host).somePermissionDenied(*perms)
    }

    /**
     * @see .somePermissionDenied
     */
    fun somePermissionDenied(host: Fragment,
                             vararg perms: String): Boolean {
        return PermissionHelper.newInstance(host).somePermissionDenied(*perms)
    }

    /**
     * Run permission callbacks on an object that requested permissions but already has them by
     * simulating [PackageManager.PERMISSION_GRANTED].
     *
     * @param object      the object requesting permissions.
     * @param requestCode the permission request code.
     * @param perms       a list of permissions requested.
     */
    private fun notifyAlreadyHasPermissions(`object`: Any,
                                            requestCode: Int,
                                            perms: Array<out String>) {
        val grantResults = IntArray(perms.size)
        for (i in perms.indices) {
            grantResults[i] = PackageManager.PERMISSION_GRANTED
        }

        onRequestPermissionsResult(requestCode, perms, grantResults, `object`)
    }
}