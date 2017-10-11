package com.lp.filemanager.activities.superclasses

import android.Manifest
import android.os.Bundle
import com.lp.easypermissions.EasyPermissions
import com.lp.easypermissions.PermissionCallbacks
import com.lp.filemanager.R

/**
 * Created by LiPin on 2017/9/21 15:15.
 * 描述：
 */
open class ThemedActivity : PreferenceActivity(), PermissionCallbacks {
    var rootMode = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //请求存储权限
        EasyPermissions.requestPermissions(this, getString(R.string.granttext), 4396,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this)
    }

    override fun onPermissionsGranted(requestCode: Int, perms: List<String>) {
    }

    override fun onPermissionsDenied(requestCode: Int, perms: List<String>) {
    }
}
