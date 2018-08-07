package com.lp.filemanager.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.lp.filemanager.R
import com.lp.filemanager.bean.CenterItem
import com.lp.filemanager.bean.HeaderItem
import com.lp.filemanager.bean.InternalStorage
import com.lp.filemanager.bean.Sdcard
import com.lp.filemanager.ui.widgets.DonutProgress
import com.lp.filemanager.utils.FileUtils
import kotlinx.android.synthetic.main.item_main_center.view.*
import kotlinx.android.synthetic.main.item_main_header.view.*

/**
 *
 * @author lipin
 * @date 2018/7/12
 */
class MainAdapter(private val context: Context, var list: List<Any>)
    : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val TAG = "MainAdapter"
    private var isCenterExpand = false
    private var isRecentShow = true

    companion object {
        const val HEADER = 0
        const val CENTER = 1
        const val RECENT = 2
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
                val view = LayoutInflater.from(context).inflate(R.layout.item_main_recent, parent, false)
                RecentViewHolder(view)
            }
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (getItemViewType(position)) {
            HEADER -> {
                bindHeaderViewHolder((holder as HeaderViewHolder), (list[0] as HeaderItem))
            }
            CENTER -> {
                bindCenterViewHolder((holder as CenterViewHolder), (list[1] as List<CenterItem>))
            }
            else -> {
                bindRecentViewHolder((holder as RecentViewHolder))
            }
        }
    }

    private fun bindRecentViewHolder(holder: RecentViewHolder) {

    }

    private fun bindHeaderViewHolder(holder: HeaderViewHolder, headerItem: HeaderItem) {
        setInternalStorage(holder.itemView.internalStorageProgress,
                holder.itemView.tvInternalStorage1, headerItem.internalStorage)
        setSdcard(holder.itemView.sdcardProgress, holder.itemView.tvSdcard1, headerItem.sdcard)
    }

    @SuppressLint("SetTextI18n")
    private fun setSdcard(progress: DonutProgress, tvSdcard1: TextView, sdcard: Sdcard) {
        if (sdcard.error == null) {
            progress.setProgress(sdcard.useProgress)
            tvSdcard1.text = FileUtils.byte2FitMemorySize(sdcard.used) + "/" +
                    FileUtils.byte2FitMemorySize(sdcard.total)
        } else {
            tvSdcard1.text = sdcard.error
        }
    }

    @SuppressLint("SetTextI18n")
    private fun setInternalStorage(progress: DonutProgress, tvInternalStorage1: TextView,
                                   internalStorage: InternalStorage) {
        if (internalStorage.error == null) {
            progress.setProgress(internalStorage.useProgress)
            tvInternalStorage1.text = FileUtils.byte2FitMemorySize(internalStorage.used) + "/" +
                    FileUtils.byte2FitMemorySize(internalStorage.total)
        } else {
            tvInternalStorage1.text = internalStorage.error
        }
    }

    private fun bindCenterViewHolder(holder: CenterViewHolder, list: List<CenterItem>) {
        val tempList = list.subList(0, 8)
        holder.itemView.rvClassify.layoutManager = GridLayoutManager(context, 4)
        val adapter = CenterItemAdapter(context, tempList)
        holder.itemView.rvClassify.adapter = adapter
        holder.itemView.ivMore.setOnClickListener {
            if (isCenterExpand) {
                rollUp(holder.itemView.ivMore, adapter, tempList)
            } else {
                expand(holder.itemView.ivMore, adapter, list)
            }
            isCenterExpand = !isCenterExpand
        }
    }

    private fun rollUp(ivMore: ImageView, adapter: CenterItemAdapter, list: List<CenterItem>) {
        ivMore.setImageResource(R.drawable.toolbar_more_list_down)
        adapter.list = list
        adapter.notifyItemRangeRemoved(8, 3)
    }

    private fun expand(ivMore: ImageView, adapter: CenterItemAdapter, list: List<CenterItem>) {
        ivMore.setImageResource(R.drawable.toolbar_more_list_up)
        adapter.list = list
        adapter.notifyItemRangeInserted(8, 3)
    }

    override fun getItemViewType(position: Int): Int {
        return when (position) {
            0 -> HEADER
            1 -> CENTER
            else -> RECENT
        }
    }

    private inner class HeaderViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    private inner class CenterViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    private inner class RecentViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

}