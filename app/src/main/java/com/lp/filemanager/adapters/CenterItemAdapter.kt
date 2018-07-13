package com.lp.filemanager.adapters

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.lp.filemanager.R
import com.lp.filemanager.bean.CenterItem
import kotlinx.android.synthetic.main.item_classify.view.*

/**
 *
 * @author lipin
 * @date 2018/7/13
 */
class CenterItemAdapter(private val context: Context, var list: List<CenterItem>)
    : RecyclerView.Adapter<CenterItemAdapter.CenterViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CenterViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_classify, parent, false)
        return CenterViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: CenterViewHolder, position: Int) {
        val item = list[position]
        holder.itemView.ivIcon.setImageResource(item.resId)
        holder.itemView.tvName.text = item.text
    }

    inner class CenterViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}