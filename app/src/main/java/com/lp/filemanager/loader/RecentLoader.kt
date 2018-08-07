package com.lp.filemanager.loader

import android.content.Context
import android.database.Cursor
import android.provider.MediaStore
import android.support.v4.content.CursorLoader


/**
 *
 * @author lipin
 * @date 2018/7/17
 */
class RecentLoader(context: Context)
    : CursorLoader(context, MediaStore.Files.getContentUri("external"),
        arrayOf(MediaStore.Files.FileColumns.DATA), null, null,
        MediaStore.Images.Media.DATE_ADDED + " DESC") {


    override fun loadInBackground(): Cursor? {
        return super.loadInBackground()
    }
}