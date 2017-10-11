package com.lp.easypermissions

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.support.annotation.NonNull
import android.support.annotation.StringRes
import android.support.v4.app.FragmentManager
import android.support.v7.app.AppCompatDialogFragment


/**
 * Created by LiPin on 2017/10/10 14:35.
 * 描述：
 */
class RationaleDialogFragmentCompat : AppCompatDialogFragment() {

    private var mPermissionCallbacks: PermissionCallbacks? = null

    companion object {
        val TAG = "RationaleDialogFragmentCompat"
        fun newInstance(
                @StringRes positiveButton: Int,
                @StringRes negativeButton: Int,
                @NonNull rationaleMsg: String,
                requestCode: Int,
                @NonNull vararg permissions: String): RationaleDialogFragmentCompat {

            // Create new Fragment
            val dialogFragment = RationaleDialogFragmentCompat()

            // Initialize configuration as arguments
            val config = RationaleDialogConfig(
                    positiveButton, negativeButton, rationaleMsg, requestCode, *permissions)
            dialogFragment.arguments = config.toBundle()

            return dialogFragment
        }
    }

    /**
     * Version of [.show] that no-ops when an IllegalStateException
     * would otherwise occur.
     */
    fun showAllowingStateLoss(manager: FragmentManager, tag: String) {

        show(manager, tag)
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if (parentFragment != null && parentFragment is PermissionCallbacks) {
            mPermissionCallbacks = parentFragment as PermissionCallbacks
        } else if (context is PermissionCallbacks) {
            mPermissionCallbacks = context
        }
    }

    override fun onDetach() {
        super.onDetach()
        mPermissionCallbacks = null
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        isCancelable = true

        val config = RationaleDialogConfig(arguments)
        val clickListener = RationaleDialogClickListener(this, config, mPermissionCallbacks)

        return config.createSupportDialog(context, clickListener)
    }
}