package com.lp.filemanager.bean

import android.support.annotation.DrawableRes

/**
 *
 * @author lipin
 * @date 2018/7/13
 */
data class HeaderItem(val internalStorage: InternalStorage, val sdcard: Sdcard)

data class InternalStorage(val useProgress: Int = 0, val total: Long, val used: Long,
                           val error: String? = null)

data class Sdcard(val useProgress: Int = 0, val total: Long, val used: Long,
                  val error: String? = null)

data class CenterItem(@DrawableRes val resId: Int, val text: String, val num: Int)