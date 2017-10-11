package com.lp.easypermissions

import android.app.Dialog
import android.app.DialogFragment
import android.app.FragmentManager
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.support.annotation.NonNull
import android.support.annotation.StringRes


/**
 * Created by LiPin on 2017/10/10 16:44.
 * 描述：
 */
class RationaleDialogFragment : DialogFragment() {

    private var mPermissionCallbacks: PermissionCallbacks? = null
    private var mStateSaved = false

    companion object {
        val TAG = "RationaleDialogFragment"
        fun newInstance(
                @StringRes positiveButton: Int,
                @StringRes negativeButton: Int,
                @NonNull rationaleMsg: String,
                requestCode: Int,
                @NonNull permissions: Array<out String>): RationaleDialogFragment {

            // Create new Fragment
            val dialogFragment = RationaleDialogFragment()

            // Initialize configuration as arguments
            val config = RationaleDialogConfig(
                    positiveButton, negativeButton, rationaleMsg, requestCode, *permissions)
            dialogFragment.arguments = config.toBundle()

            return dialogFragment
        }
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1
                && parentFragment != null
                && parentFragment is PermissionCallbacks) {
            mPermissionCallbacks = parentFragment as PermissionCallbacks
        } else if (context is PermissionCallbacks) {
            mPermissionCallbacks = context
        }
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        mStateSaved = true
        super.onSaveInstanceState(outState)
    }

    /**
     * Version of {@link #show(FragmentManager, String)} that no-ops when an IllegalStateException
     * would otherwise occur.
     */
    fun showAllowingStateLoss(manager: FragmentManager, tag: String) {

        if (mStateSaved) {
            return
        }
        show(manager, tag)
    }

    override fun onDetach() {
        super.onDetach()
        mPermissionCallbacks = null
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        // Rationale dialog should not be cancelable
        isCancelable = false

        // Get config from arguments, create click listener
        val config = RationaleDialogConfig(arguments)
        val clickListener = RationaleDialogClickListener(this, config, mPermissionCallbacks)

        // Create an AlertDialog
        return config.createFrameworkDialog(activity, clickListener)
    }
}