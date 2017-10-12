package com.lp.filemanager.utils

import android.Manifest
import android.annotation.TargetApi
import android.content.Context
import android.hardware.usb.UsbManager
import android.os.Build
import android.os.Environment
import android.preference.PreferenceManager
import android.text.TextUtils
import android.util.Log
import com.lp.easypermissions.EasyPermissions
import com.lp.filemanager.activities.superclasses.ThemedActivity
import java.io.File
import java.util.regex.Pattern

/**
 * Created by LiPin on 2017/10/11 17:28.
 * 描述：
 */
object FileUtils {

    val DIR_SEPARATOR = Pattern.compile("/")

    fun getStorageDirectories(context: Context): ArrayList<String> {

        //路径集合
        val rv = ArrayList<String>()
        //
        val rawExternalStorage = System.getenv("EXTERNAL_STORAGE")
        //
        val rawSecondaryStoragesStr = System.getenv("SECONDARY_STORAGE")
        //
        val rawEmulatedStorageTarget = System.getenv("EMULATED_STORAGE_TARGET")
        if (TextUtils.isEmpty(rawEmulatedStorageTarget)) {
            if (TextUtils.isEmpty(rawExternalStorage)) {
                rv.add("/storage/sdcard0")
            } else {
                rv.add(rawExternalStorage)
            }
        } else {
            val rawUserId: String
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN_MR1) {
                rawUserId = ""
            } else {
                val path = Environment.getExternalStorageDirectory().absolutePath
                val folders = DIR_SEPARATOR.split(path)
                val lastFolders = folders[folders.size - 1]
                var isDigit = false
                try {
                    lastFolders.toInt()
                    isDigit = true
                } catch (e: NumberFormatException) {
                }
                rawUserId = if (isDigit) lastFolders else ""
            }
            if (TextUtils.isEmpty(rawUserId)) {
                rv.add(rawEmulatedStorageTarget)
            } else {
                rv.add(rawEmulatedStorageTarget + File.separator + rawUserId)
            }
        }

        if (!TextUtils.isEmpty(rawSecondaryStoragesStr)) {
            val rawSecondaryStorages = rawSecondaryStoragesStr.split(File.separator)
            rv.addAll(rawSecondaryStorages)
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && hasStoragePermission(context)) {
            rv.clear()
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            val strings = getExtSdCardPathsForActivity(context)
            for (s in strings) {
                val file = File(s)
                if (!rv.contains(s) && canListFiles(file)) {
                    rv.add(s)
                }
            }
        }
        if (ThemedActivity.rootMode) {
            rv.add("/")
        }
        val usb = getUsbDrive()
        if (usb != null && !rv.contains(usb.path)) {
            rv.add(usb.path)
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT && isUsbDeviceConnected(context)) {
            rv.add(Constant.PREFIX_OTG + "/")
        }

        return rv
    }

    private fun isUsbDeviceConnected(context: Context): Boolean {
        val usbManager: UsbManager = context.getSystemService(Context.USB_SERVICE) as UsbManager
        return if (usbManager.deviceList.size > 0) {
            PreferenceManager.getDefaultSharedPreferences(context).edit()
                    .putString(Constant.KEY_PREF_OTG, Constant.VALUE_PREF_OTG_NULL).apply()
            true
        } else {
            PreferenceManager.getDefaultSharedPreferences(context).edit()
                    .putString(Constant.KEY_PREF_OTG, "").apply()
            false
        }
    }

    fun getUsbDrive(): File? {
        var parent = File("/storage")
        parent.listFiles()
                .filter { it.exists() && it.name.toLowerCase().contains("usb") && it.canExecute() }
                .forEach { return it }
        parent = File("/mnt/sdcard/usbStorage")
        if (parent.exists() && parent.canExecute()) {
            return parent
        }
        parent = File("/mnt/sdcard/usb_storage")
        if (parent.exists() && parent.canExecute()) {
            return parent
        }
        return null
    }

    private fun canListFiles(file: File): Boolean = file.canRead() && file.isDirectory

    @TargetApi(Build.VERSION_CODES.KITKAT)
    private fun getExtSdCardPathsForActivity(context: Context): Array<String> {
        val paths = ArrayList<String>()
        for (file in context.getExternalFilesDirs("external")) {
            if (file != null) {
                val index = file.absolutePath.lastIndexOf("/Android/data")
                if (index < 0) {
                    Log.w("FileUtils", "Unexpected external file dir: " + file.absolutePath)
                } else {
                    var path = file.absolutePath.substring(0, index)
                    path = File(path).canonicalPath
                    paths.add(path)
                }
            }
        }
        if (paths.isEmpty()) {
            paths.add("/storage/sdcard1")
        }

        return paths.toArray(emptyArray<String>())
    }

    private fun hasStoragePermission(context: Context): Boolean =
            EasyPermissions.hasPermissions(context, Manifest.permission.WRITE_EXTERNAL_STORAGE)
}