package com.lp.easypermissions

import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.support.annotation.NonNull
import android.support.annotation.StringRes
import android.support.v7.app.AlertDialog


/**
 * Created by LiPin on 2017/10/10 15:05.
 * 描述：
 */
class RationaleDialogConfig {

    private val KEY_POSITIVE_BUTTON = "positiveButton"
    private val KEY_NEGATIVE_BUTTON = "negativeButton"
    private val KEY_RATIONALE_MESSAGE = "rationaleMsg"
    private val KEY_REQUEST_CODE = "requestCode"
    private val KEY_PERMISSIONS = "permissions"

    var positiveButton: Int = 0
    var negativeButton: Int = 0
    var requestCode: Int = 0
    var rationaleMsg: String? = null
    var permissions: Array<out String>? = null

    constructor(@StringRes positiveButton: Int, @StringRes negativeButton: Int,
                @NonNull rationaleMsg: String, requestCode: Int,
                @NonNull vararg permissions: String) {

        this.positiveButton = positiveButton
        this.negativeButton = negativeButton
        this.rationaleMsg = rationaleMsg
        this.requestCode = requestCode
        this.permissions = permissions
    }

    constructor(bundle: Bundle) {
        positiveButton = bundle.getInt(KEY_POSITIVE_BUTTON)
        negativeButton = bundle.getInt(KEY_NEGATIVE_BUTTON)
        rationaleMsg = bundle.getString(KEY_RATIONALE_MESSAGE)
        requestCode = bundle.getInt(KEY_REQUEST_CODE)
        permissions = bundle.getStringArray(KEY_PERMISSIONS)
    }

    fun toBundle(): Bundle {
        val bundle = Bundle()
        bundle.putInt(KEY_POSITIVE_BUTTON, positiveButton)
        bundle.putInt(KEY_NEGATIVE_BUTTON, negativeButton)
        bundle.putString(KEY_RATIONALE_MESSAGE, rationaleMsg)
        bundle.putInt(KEY_REQUEST_CODE, requestCode)
        bundle.putStringArray(KEY_PERMISSIONS, permissions)

        return bundle
    }

    fun createSupportDialog(context: Context, listener: DialogInterface.OnClickListener): AlertDialog {
        return AlertDialog.Builder(context)
                .setCancelable(false)
                .setPositiveButton(positiveButton, listener)
                .setNegativeButton(negativeButton, listener)
                .setMessage(rationaleMsg)
                .create()
    }

    fun createFrameworkDialog(context: Context, listener: DialogInterface.OnClickListener): android.app.AlertDialog {
        return android.app.AlertDialog.Builder(context)
                .setCancelable(false)
                .setPositiveButton(positiveButton, listener)
                .setNegativeButton(negativeButton, listener)
                .setMessage(rationaleMsg)
                .create()
    }
}