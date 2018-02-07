package com.lp.filemanager.utils

import android.Manifest
import android.content.Context
import android.support.annotation.StringRes
import android.widget.Toast
import com.lp.easypermissions.EasyPermissions

/**
 *
 * @author someone
 * @date 2018/2/6
 *
 */
fun Context.showToast(text: CharSequence) {
    Toast.makeText(this, text, Toast.LENGTH_SHORT).show()
}

fun Context.showToast(@StringRes resId: Int) {
    Toast.makeText(this, resId, Toast.LENGTH_SHORT).show()
}

fun Context.hasStoragePermission(): Boolean =
        EasyPermissions.hasPermissions(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)