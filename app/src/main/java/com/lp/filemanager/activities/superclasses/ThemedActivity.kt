package com.lp.filemanager.activities.superclasses

import android.Manifest
import android.content.Intent
import android.os.Bundle
import android.util.Log
import com.lp.easypermissions.AppSettingsDialog
import com.lp.easypermissions.EasyPermissions
import com.lp.easypermissions.PermissionCallbacks
import com.lp.filemanager.R
import com.lp.filemanager.utils.hasStoragePermission
import com.lp.filemanager.utils.showToast


/**
 * Created by LiPin on 2017/9/21 15:15.
 * 描述：
 */
open class ThemedActivity : PreferenceActivity(), PermissionCallbacks {

    private val TAG = "ThemedActivity"

    companion object {
        var rootMode = false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //请求存储权限
        EasyPermissions.requestPermissions(this, getString(R.string.granttext), 4396,
                Manifest.permission.READ_EXTERNAL_STORAGE)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this)
    }

    override fun onPermissionsGranted(requestCode: Int, perms: List<String>) {
        Log.d(TAG, "onPermissionsGranted:" + requestCode + ":" + perms.size)
    }

    override fun onPermissionsDenied(requestCode: Int, perms: List<String>) {
        Log.d(TAG, "onPermissionsDenied:" + requestCode + ":" + perms.size)
        if (EasyPermissions.somePermissionPermanentlyDenied(this, *perms.toTypedArray())) {
            AppSettingsDialog.Builder(this).build().show()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == AppSettingsDialog.DEFAULT_SETTINGS_REQ_CODE) {
            val yes = "允许"
            val no = "拒绝"
            showToast(getString(R.string.returned_from_app_settings_to_activity,
                    if (hasStoragePermission()) yes else no))
        }
    }

}
