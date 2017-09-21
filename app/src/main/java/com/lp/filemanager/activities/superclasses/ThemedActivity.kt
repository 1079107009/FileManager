package com.lp.filemanager.activities.superclasses

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import com.afollestad.materialdialogs.DialogAction
import com.lp.filemanager.R
import com.lp.filemanager.ui.dialogs.GeneralDialogCreation

/**
 * Created by LiPin on 2017/9/21 15:15.
 * 描述：
 */
open class ThemedActivity : PreferenceActivity() {

    var rootMode = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //请求存储权限
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && !checkStoragePermission()) {
            requestStoragePermission()
        }
    }

    private fun requestStoragePermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            val materialDialog = GeneralDialogCreation.showBasicDialog(this, arrayOf(
                    getString(R.string.granttext),
                    getString(R.string.grantper),
                    getString(R.string.grant),
                    getString(R.string.cancel)))
            materialDialog.getActionButton(DialogAction.POSITIVE).setOnClickListener {
                requestPermission()
            }
            materialDialog.getActionButton(DialogAction.NEGATIVE).setOnClickListener {
                finish()
            }
            materialDialog.setCancelable(false)
            materialDialog.show()
        } else {
            requestPermission()
        }
    }

    private fun checkStoragePermission() = ActivityCompat
            .checkSelfPermission(this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED

    private fun requestPermission() {
        ActivityCompat.requestPermissions(this,
                arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE), 77)
    }
}