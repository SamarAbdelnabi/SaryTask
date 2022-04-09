package com.android.sary.ui.storeFragment.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.sary.common.utils.showIf
import com.android.sary.data.entity.CatalogResult
import com.android.sary.data.entity.Data
import com.android.sary.data.entity.DataType
import com.android.sary.data.entity.UiType
import com.android.sary.databinding.GroupDataTypeLayoutBinding
import com.android.sary.databinding.SmartDataTypeLayoutBinding

class CatalogAdapter(
    private val context: Context,
    var list: MutableList<CatalogResult> = mutableListOf(),
    private val doOnItemClicked: ((Data) -> Unit)? = null
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): RecyclerView.ViewHolder {
        return when (viewType) {
            DataType.SMART.ordinal -> {
                SmartDataTypeViewHolder(
                    SmartDataTypeLayoutBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                )
            }
            else -> {
                GroupDataTypeViewHolder(
                    GroupDataTypeLayoutBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                )
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val header = list[position]
        when (holder) {
            is SmartDataTypeViewHolder -> {
                holder.bind(header)
            }
            is GroupDataTypeViewHolder -> {
                holder.bind(header)
            }
        }
    }

    override fun getItemCount() = list.size

    override fun getItemViewType(position: Int): Int {
        return list[position].getDataType().ordinal
    }

    inner class SmartDataTypeViewHolder(
        val binding: SmartDataTypeLayoutBinding,
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: CatalogResult) {
            binding.title.text = item.title.orEmpty()
            binding.title.showIf { item.showTitle?:false && !item.data.isNullOrEmpty() }

            val layoutManager = if (item.getUiType() == UiType.GRID)
                GridLayoutManager(context, item.rowCount ?: 0)

            else LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)

            binding.itemsRecyclerView.layoutManager = layoutManager

            binding.itemsRecyclerView.adapter =
                SmartAdapter(
                    context,
                    item.data,
                    context.resources.displayMetrics.widthPixels.div(item.rowCount ?: 1) - 30,
                    doOnItemClicked
                )

        }
    }

    inner class GroupDataTypeViewHolder(
        val binding: GroupDataTypeLayoutBinding,
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: CatalogResult) {
            binding.title.text = item.title.orEmpty()
            binding.title.showIf { item.showTitle?:false && !item.data.isNullOrEmpty() }

            val layoutManager = if (item.getUiType() == UiType.GRID)
                GridLayoutManager(context, item.rowCount ?: 0)

            else LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)

            binding.itemsRecyclerView.layoutManager = layoutManager
            binding.itemsRecyclerView.adapter =
                GroupAdapter(
                    context,
                    item.data,
                    context.resources.displayMetrics.widthPixels.div(item.rowCount ?: 1) - 30,
                    doOnItemClicked
                )
        }
    }
}