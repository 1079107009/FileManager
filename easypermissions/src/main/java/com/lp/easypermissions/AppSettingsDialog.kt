package com.lp.easypermissions

import android.app.Activity
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.os.Parcel
import android.os.Parcelable
import android.support.annotation.NonNull
import android.support.annotation.Nullable
import android.support.annotation.StringRes
import android.support.annotation.StyleRes
import android.support.v4.app.Fragment
import android.support.v7.app.AlertDialog
import android.text.TextUtils


/**
 * Created by LiPin on 2017/10/11 14:39.
 * 描述：
 */
class AppSettingsDialog : Parcelable {

    @StyleRes
    private var mThemeResId: Int = 0
    private var mRationale: String? = null
    private var mTitle: String? = null
    private var mPositiveButtonText: String? = null
    private var mNegativeButtonText: String? = null
    private var mRequestCode: Int = 0

    private var mActivityOrFragment: Any? = null
    private var mContext: Context? = null

    private constructor(@NonNull activityOrFragment: Any,
                        @StyleRes themeResId: Int,
                        @Nullable rationale: String,
                        @Nullable title: String,
                        @Nullable positiveButtonText: String,
                        @Nullable negativeButtonText: String,
                        requestCode: Int) {
        setActivityOrFragment(activityOrFragment)
        mThemeResId = themeResId
        mRationale = rationale
        mTitle = title
        mPositiveButtonText = positiveButtonText
        mNegativeButtonText = negativeButtonText
        mRequestCode = requestCode
    }

    private fun setActivityOrFragment(activityOrFragment: Any) {
        mActivityOrFragment = activityOrFragment
        mContext = when (activityOrFragment) {
            is Activity -> activityOrFragment
            is Fragment -> activityOrFragment.context
            else -> throw IllegalStateException("Unknown object: " + activityOrFragment)
        }
    }

    private fun startForResult(intent: Intent) {
        when (mActivityOrFragment) {
            is Activity -> (mActivityOrFragment as Activity).startActivityForResult(intent, mRequestCode)
            is Fragment -> (mActivityOrFragment as Fragment).startActivityForResult(intent, mRequestCode)
        }
    }

    /**
     * Display the built dialog.
     */
    fun show() {
        startForResult(AppSettingsDialogHolderActivity.createShowDialogIntent(mContext, this))
    }

    fun showDialog(positiveListener: DialogInterface.OnClickListener,
                   negativeListener: DialogInterface.OnClickListener): AlertDialog {

        val builder = if (mThemeResId > 0) {
            AlertDialog.Builder(mContext!!, mThemeResId)
        } else {
            AlertDialog.Builder(mContext!!)
        }

        return builder.setCancelable(false)
                .setTitle(mTitle)
                .setMessage(mRationale)
                .setPositiveButton(mPositiveButtonText, positiveListener)
                .setNegativeButton(mNegativeButtonText, negativeListener)
                .show()
    }

    private constructor(parcel: Parcel) {
        mThemeResId = parcel.readInt()
        mRationale = parcel.readString()
        mTitle = parcel.readString()
        mPositiveButtonText = parcel.readString()
        mNegativeButtonText = parcel.readString()
        mRequestCode = parcel.readInt()
    }

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeInt(mThemeResId)
        dest.writeString(mRationale)
        dest.writeString(mTitle)
        dest.writeString(mPositiveButtonText)
        dest.writeString(mNegativeButtonText)
        dest.writeInt(mRequestCode)
    }

    override fun describeContents(): Int = 0

    companion object CREATOR : Parcelable.Creator<AppSettingsDialog> {

        val DEFAULT_SETTINGS_REQ_CODE = 16061
        val EXTRA_APP_SETTINGS = "extra_app_settings"

        override fun createFromParcel(parcel: Parcel): AppSettingsDialog = AppSettingsDialog(parcel)

        override fun newArray(size: Int): Array<AppSettingsDialog?> = arrayOfNulls(size)

        fun fromIntent(intent: Intent, activity: Activity): AppSettingsDialog {
            val dialog = intent.getParcelableExtra<AppSettingsDialog>(AppSettingsDialog.EXTRA_APP_SETTINGS)
            dialog.setActivityOrFragment(activity)
            return dialog
        }
    }

    open class Builder {

        private var mActivityOrFragment: Any? = null
        private var mContext: Context? = null
        @StyleRes
        private var mThemeResId = -1
        private var mRationale: String? = null
        private var mTitle: String? = null
        private var mPositiveButtonText: String? = null
        private var mNegativeButtonText: String? = null
        private var mRequestCode = -1

        constructor(@NonNull activity: Activity) {
            mActivityOrFragment = activity
            mContext = activity
        }

        constructor(@NonNull fragment: Fragment) {
            mActivityOrFragment = fragment
            mContext = fragment.context
        }

        fun setThemeResId(@StyleRes themeResId: Int): Builder {
            mThemeResId = themeResId
            return this
        }

        fun setTitle(title: String): Builder {
            mTitle = title
            return this
        }


        /**
         * Set the title dialog. Default is "Permissions Required".
         */
        fun setTitle(@StringRes title: Int): Builder {
            mTitle = mContext?.getString(title)
            return this
        }

        /**
         * Set the rationale dialog. Default is
         * "This app may not work correctly without the requested permissions.
         * Open the app settings screen to modify app permissions."
         */
        fun setRationale(rationale: String): Builder {
            mRationale = rationale
            return this
        }

        /**
         * Set the rationale dialog. Default is
         * "This app may not work correctly without the requested permissions.
         * Open the app settings screen to modify app permissions."
         */
        fun setRationale(@StringRes rationale: Int): Builder {
            mRationale = mContext?.getString(rationale)
            return this
        }

        /**
         * Set the positive button text, default is [android.R.string.ok].
         */
        fun setPositiveButton(text: String): Builder {
            mPositiveButtonText = text
            return this
        }

        /**
         * Set the positive button text, default is [android.R.string.ok].
         */
        fun setPositiveButton(@StringRes textId: Int): Builder {
            mPositiveButtonText = mContext?.getString(textId)
            return this
        }

        /**
         * Set the negative button text, default is [android.R.string.cancel].
         *
         *
         * To know if a user cancelled the request, check if your permissions were given with [ ][EasyPermissions.hasPermissions] in [ ][Activity.onActivityResult]. If you still don't have the right
         * permissions, then the request was cancelled.
         */
        fun setNegativeButton(text: String): Builder {
            mNegativeButtonText = text
            return this
        }

        /**
         * Set the negative button text, default is [android.R.string.cancel].
         */
        fun setNegativeButton(@StringRes textId: Int): Builder {
            mNegativeButtonText = mContext?.getString(textId)
            return this
        }

        /**
         * Set the request code use when launching the Settings screen for result, can be retrieved
         * in the calling Activity's [Activity.onActivityResult] method.
         * Default is [.DEFAULT_SETTINGS_REQ_CODE].
         */
        fun setRequestCode(requestCode: Int): Builder {
            mRequestCode = requestCode
            return this
        }

        /**
         * Build the [AppSettingsDialog] from the specified options. Generally followed by a
         * call to [AppSettingsDialog.show].
         */
        fun build(): AppSettingsDialog {
            mRationale = if (TextUtils.isEmpty(mRationale))
                mContext?.getString(R.string.rationale_ask_again)
            else
                mRationale
            mTitle = if (TextUtils.isEmpty(mTitle))
                mContext?.getString(R.string.title_settings_dialog)
            else
                mTitle
            mPositiveButtonText = if (TextUtils.isEmpty(mPositiveButtonText))
                mContext?.getString(R.string.ok)
            else
                mPositiveButtonText
            mNegativeButtonText = if (TextUtils.isEmpty(mNegativeButtonText))
                mContext?.getString(R.string.cancel)
            else
                mNegativeButtonText
            mRequestCode = if (mRequestCode > 0) mRequestCode else DEFAULT_SETTINGS_REQ_CODE

            return AppSettingsDialog(
                    mActivityOrFragment!!,
                    mThemeResId,
                    mRationale!!,
                    mTitle!!,
                    mPositiveButtonText!!,
                    mNegativeButtonText!!,
                    mRequestCode)
        }
    }
}