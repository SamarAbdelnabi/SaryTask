package com.android.sary.ui.storeFragment.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.android.sary.common.utils.hide
import com.android.sary.common.utils.onClick
import com.android.sary.data.entity.Data
import com.android.sary.databinding.GroupItemBinding
import com.bumptech.glide.Glide

class GroupAdapter(
    private val context: Context,
var list: MutableList<Data> = mutableListOf(),
var itemWidth: Int,
private val doOnItemClicked: ((Data) -> Unit)? = null
) : RecyclerView.Adapter<GroupAdapter.GroupTypeViewHolder>() {

    inner class GroupTypeViewHolder(private val binding: GroupItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Data) {
            binding.itemTitle.text = item.name
            Glide.with(context)
                .load(item.image)
                .into(binding.itemImg)

            binding.layout.layoutParams.width = itemWidth
            binding.layout.onClick { doOnItemClicked?.invoke(item) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GroupTypeViewHolder {
        return GroupTypeViewHolder(
            GroupItemBinding.inflate(
                LayoutInflater.from(
                    parent.context
                ), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: GroupTypeViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount() = list.size
}