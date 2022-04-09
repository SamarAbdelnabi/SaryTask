package com.android.sary.ui.storeFragment.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.android.sary.common.utils.onClick
import com.android.sary.data.entity.Data
import com.android.sary.databinding.SmartItemBinding
import com.bumptech.glide.Glide


class SmartAdapter(
    private val context: Context,
    var list: MutableList<Data> = mutableListOf(),
    var itemWidth: Int,
    private val doOnItemClicked: ((Data) -> Unit)? = null
) : RecyclerView.Adapter<SmartAdapter.SmartTypeViewHolder>() {

    inner class SmartTypeViewHolder(private val binding: SmartItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Data) {
            binding.itemTitle.text = item.name

            Glide.with(context)
                .load(item.image)
                .into(binding.itemImg)

            binding.layout.layoutParams.width = itemWidth
            binding.circleLayout.layoutParams.height = itemWidth
            binding.layout.onClick { doOnItemClicked?.invoke(item) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SmartTypeViewHolder {
        return SmartTypeViewHolder(
            SmartItemBinding.inflate(
                LayoutInflater.from(
                    parent.context
                ), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: SmartTypeViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount() = list.size
}