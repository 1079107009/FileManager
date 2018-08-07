package com.lp.filemanager.viewmodel

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.MutableLiveData
import android.os.Environment
import android.provider.MediaStore
import android.provider.MediaStore.MediaColumns
import android.util.Log
import com.lp.filemanager.R
import com.lp.filemanager.application.FileApplication
import com.lp.filemanager.bean.CenterItem
import com.lp.filemanager.bean.HeaderItem
import com.lp.filemanager.bean.InternalStorage
import com.lp.filemanager.bean.Sdcard
import com.lp.filemanager.utils.FileUtils
import java.io.File


/**
 *
 * @author lipin
 * @date 2018/7/13
 */
class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val QUERY_URI = MediaStore.Files.getContentUri("external")

    private val PROJECTION = arrayOf(
            MediaStore.Files.FileColumns.DATA
//            MediaStore.MediaColumns.DISPLAY_NAME,
//            MediaStore.MediaColumns.MIME_TYPE,
//            MediaStore.MediaColumns.SIZE,
//            MediaStore.MediaColumns.DATE_ADDED
    )

//    private val SELECTION_ALL = (
//            "(" + MediaStore.Files.FileColumns.MEDIA_TYPE + "=?"
//                    + " OR "
//                    + MediaStore.Files.FileColumns.MEDIA_TYPE + "=?"
//                    + " OR "
//                    + MediaStore.Files.FileColumns.MEDIA_TYPE + "=?"
//                    + " OR "
//                    + MediaStore.Files.FileColumns.MEDIA_TYPE + "=?)"
//                    + " AND " + MediaStore.MediaColumns.SIZE + ">0")
//
//    private val SELECTION_ALL_ARGS = arrayOf(
//            MediaStore.Files.FileColumns.MEDIA_TYPE_IMAGE.toString(),
//            MediaStore.Files.FileColumns.MEDIA_TYPE_VIDEO.toString(),
//            MediaStore.Files.FileColumns.MEDIA_TYPE_AUDIO.toString(),
//            MediaStore.Files.FileColumns.MEDIA_TYPE_NONE.toString()
//    )


    val liveData = MutableLiveData<List<Any>>()

    fun getMainData() {
        val list = ArrayList<Any>()
        list.add(getHeaderData())
        list.add(getCenterData())
        test()
        liveData.value = list
    }

    private fun test() {
        val cursor = getApplication<FileApplication>().contentResolver.query(
                QUERY_URI,
                PROJECTION,
                null,
                null,
                MediaStore.Audio.Media.DEFAULT_SORT_ORDER
        )
        while (cursor.moveToNext()) {
            val dataColumn = cursor.getColumnIndexOrThrow(MediaColumns.DISPLAY_NAME)
            val filePath = cursor.getString(dataColumn)
            Log.e("cursor", filePath)
        }
        cursor.close()
    }

    private fun getCenterData(): List<CenterItem> {
        val list = ArrayList<CenterItem>()
        list.add(CenterItem(R.drawable.library_app, "应用", 0))
        list.add(CenterItem(R.drawable.library_image, "图片", 0))
        list.add(CenterItem(R.drawable.library_musicplay, "音乐", 0))
        list.add(CenterItem(R.drawable.library_video, "视频", 0))
        list.add(CenterItem(R.drawable.library_document, "文档", 0))
        list.add(CenterItem(R.drawable.library_download, "下载", 0))
        list.add(CenterItem(R.drawable.library_compress, "压缩管理", 0))
        list.add(CenterItem(R.drawable.library_apk, "安装包", 0))
        list.add(CenterItem(R.drawable.library_sender, "快传", 0))
        list.add(CenterItem(R.drawable.library_logger_new, "日志", 0))
        list.add(CenterItem(R.drawable.library_cache, "缓存", 0))
        return list
    }

    private fun getHeaderData(): HeaderItem {
        return HeaderItem(getInternalStorage(), getSdcard())
    }

    private fun getSdcard(): Sdcard {
        val paths = FileUtils.getSDCardPaths(getApplication(), true)
        return if (paths.isNotEmpty()) {
            val file = File(paths[0])
            val totalSize = FileUtils.getTotalExternalMemorySize(file)
            val usableSize = FileUtils.getUsableExternalMemorySize(file)
            if (totalSize != 0L) {
                val useProgress = usableSize.toFloat() / totalSize.toFloat() * 100
                Sdcard(Math.round(useProgress), totalSize, usableSize, null)
            } else {
                Sdcard(0, 0, 0,
                        getApplication<FileApplication>().getString(R.string.no_sdcard))
            }
        } else {
            Sdcard(0, 0, 0,
                    getApplication<FileApplication>().getString(R.string.sdcard_not_available))
        }
    }

    private fun getInternalStorage(): InternalStorage {
        return if (FileUtils.isSDCardEnableByEnvironment()) {
            val file = Environment.getExternalStorageDirectory()
            val totalSize = FileUtils.getTotalExternalMemorySize(file)
            val usableSize = FileUtils.getUsableExternalMemorySize(file)
            if (totalSize != 0L) {
                val useProgress = usableSize.toFloat() / totalSize.toFloat() * 100
                InternalStorage(Math.round(useProgress), totalSize, usableSize, null)
            } else {
                InternalStorage(0, 0, 0,
                        getApplication<FileApplication>().getString(R.string.internal_storage_not_available))
            }
        } else {
            InternalStorage(0, 0, 0,
                    getApplication<FileApplication>().getString(R.string.internal_storage_not_available))
        }
    }

    override fun onCleared() {
        super.onCleared()

    }
}