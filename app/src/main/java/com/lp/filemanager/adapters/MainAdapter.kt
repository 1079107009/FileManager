package com.lp.filemanager.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.lp.filemanager.R
import com.lp.filemanager.ui.widgets.DonutProgress
import com.lp.filemanager.utils.FileUtils
import com.lp.filemanager.utils.SDCardUtils
import kotlinx.android.synthetic.main.item_main_header.view.*
import java.io.File

/**
 *
 * @author lipin
 * @date 2018/7/12
 */
class MainAdapter(private val context: Context) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val TAG = "MainAdapter"

    companion object {
        const val HEADER = 0
        const val CENTER = 1
        const val NORMAL = 2
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            HEADER -> {
                val view = LayoutInflater.from(context).inflate(R.layout.item_main_header, parent, false)
                HeaderViewHolder(view)
            }
            CENTER -> {
                val view = LayoutInflater.from(context).inflate(R.layout.item_main_center, parent, false)
                CenterViewHolder(view)
            }
            else -> {
                val view = LayoutInflater.from(context).inflate(R.layout.item_main_header, parent, false)
                NormalViewHolder(view)
            }
        }
    }

    override fun getItemCount(): Int {
        return 1
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (getItemViewType(position)) {
            HEADER -> {
                bindHeaderViewHolder((holder as HeaderViewHolder))
            }
            CENTER -> {
                bindCenterViewHolder((holder as CenterViewHolder))
            }
            else -> {
                bindNormalViewHolder((holder as NormalViewHolder))
            }
        }
    }

    private fun bindHeaderViewHolder(holder: HeaderViewHolder) {
        setInternalStorage(holder.itemView.internalStorageProgress, holder.itemView.tvInternalStorage1)
        setSdcard(holder.itemView.sdcardProgress, holder.itemView.tvSdcard1)
    }

    @SuppressLint("SetTextI18n")
    private fun setSdcard(progress: DonutProgress, tvSdcard1: TextView) {
        val paths = SDCardUtils.getSDCardPaths(context, true)
        if (paths.isNotEmpty()) {
            val file = File(paths[0])
            val totalSize = file.totalSpace
            val usableSize = totalSize - file.usableSpace
            if (totalSize != 0L) {
                val useProgress = usableSize.toFloat() / totalSize.toFloat() * 100
                progress.setProgress(Math.round(useProgress))
                tvSdcard1.text = FileUtils.byte2FitMemorySize(usableSize) + "/" + FileUtils.byte2FitMemorySize(totalSize)
            } else {
                tvSdcard1.setText(R.string.no_sdcard)
            }
        } else {
            tvSdcard1.setText(R.string.sdcard_not_available)
        }
    }

    @SuppressLint("SetTextI18n")
    private fun setInternalStorage(progress: DonutProgress, tvInternalStorage1: TextView) {
        if (SDCardUtils.isSDCardEnableByEnvironment()) {
            val totalSize = SDCardUtils.getTotalExternalMemorySize()
            val usableSize = totalSize - SDCardUtils.getUsableExternalMemorySize()
            if (totalSize != 0L) {
                val useProgress = usableSize.toFloat() / totalSize.toFloat() * 100
                progress.setProgress(Math.round(useProgress))
            }
            tvInternalStorage1.text = FileUtils.byte2FitMemorySize(usableSize) + "/" + FileUtils.byte2FitMemorySize(totalSize)
        } else {
            tvInternalStorage1.setText(R.string.internal_storage_not_available)
        }
    }

    private fun bindCenterViewHolder(holder: CenterViewHolder) {

    }

    private fun bindNormalViewHolder(holder: NormalViewHolder) {

    }

    override fun getItemViewType(position: Int): Int {
        return when (position) {
            0 -> HEADER
            1 -> CENTER
            else -> NORMAL
        }
    }

    private inner class HeaderViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    private inner class CenterViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    private inner class NormalViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}