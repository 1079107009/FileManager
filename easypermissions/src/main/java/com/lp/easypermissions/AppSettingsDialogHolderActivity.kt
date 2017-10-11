package com.lp.easypermissions

import android.app.Activity
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity


/**
 * Created by LiPin on 2017/10/11 14:39.
 * 描述：
 */
class AppSettingsDialogHolderActivity : AppCompatActivity(), DialogInterface.OnClickListener {

    companion object {
        private val APP_SETTINGS_RC = 7534

        fun createShowDialogIntent(context: Context?, dialog: AppSettingsDialog): Intent {
            return Intent(context, AppSettingsDialogHolderActivity::class.java)
                    .putExtra(AppSettingsDialog.EXTRA_APP_SETTINGS, dialog)
        }
    }

    private var mDialog: AlertDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mDialog = AppSettingsDialog.fromIntent(intent, this).showDialog(this, this)
    }

    override fun onDestroy() {
        super.onDestroy()
        if (mDialog != null && mDialog!!.isShowing) {
            mDialog!!.dismiss()
        }
    }

    override fun onClick(dialog: DialogInterface?, which: Int) {
        when (which) {
            DialogInterface.BUTTON_POSITIVE -> {
                startActivityForResult(
                        Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                                .setData(Uri.fromParts("package", packageName, null))
                        , APP_SETTINGS_RC)
            }
            DialogInterface.BUTTON_NEGATIVE -> {
                setResult(Activity.RESULT_CANCELED)
                finish()
            }
            else -> throw IllegalStateException("Unknown button type: " + which)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        setResult(resultCode, data)
        finish()
    }
}