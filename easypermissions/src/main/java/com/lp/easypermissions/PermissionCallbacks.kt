package com.lp.easypermissions

import android.support.v4.app.ActivityCompat

/**
 * Created by LiPin on 2017/10/10 9:16.
 * 描述：权限回调接口
 */
interface PermissionCallbacks : ActivityCompat.OnRequestPermissionsResultCallback {
    fun onPermissionsGranted(requestCode: Int, perms: List<String>)

    fun onPermissionsDenied(requestCode: Int, perms: List<String>)
}