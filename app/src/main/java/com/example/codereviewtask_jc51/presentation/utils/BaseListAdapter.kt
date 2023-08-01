package com.example.codereviewtask_jc51.presentation.utils

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.AsyncDifferConfig
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import java.util.concurrent.Executors

open class BaseListAdapter<ListItemType : Any, ViewBindingType : ViewDataBinding>(
    private val itemLayoutId : Int,
    private val bindingItemId : Int,
    private val bindingMap :HashMap<Int, Any> = HashMap(),
    areItemsSameLambda : (oldItem: ListItemType, newItem: ListItemType) -> Boolean = { oldItem, newItem -> oldItem.equals(newItem) },
    areContentsTheSame : (oldItem: ListItemType, newItem: ListItemType) -> Boolean = { oldItem, newItem -> oldItem == newItem },
    diffCallback : DiffUtil.ItemCallback<ListItemType> = object :
        DiffUtil.ItemCallback<ListItemType>() {
        override fun areItemsTheSame(oldItem: ListItemType, newItem: ListItemType) =
            areItemsSameLambda(oldItem, newItem)

        override fun areContentsTheSame(oldItem: ListItemType, newItem: ListItemType) =
            areContentsTheSame(oldItem, newItem)
    }
) : ListAdapter<ListItemType, BaseListAdapter<ListItemType, ViewBindingType>.BaseViewHolder>(
    AsyncDifferConfig.Builder(diffCallback)
        .setBackgroundThreadExecutor(Executors.newSingleThreadExecutor())
        .build()
) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BaseViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = DataBindingUtil.inflate<ViewBindingType>(
            layoutInflater,
            itemLayoutId,
            parent,
            false
        )
        return BaseViewHolder(binding)
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        holder.binding.run {
            setVariable(bindingItemId, getItem(position))
            bindingMap.keys.forEach {
                setVariable(it, bindingMap[it])
            }
            onBind(this, getItem(position), position)
        }
    }

    open fun onBind(binding: ViewBindingType, item: ListItemType, position: Int){}

    open inner class BaseViewHolder constructor(val binding: ViewBindingType) :
        RecyclerView.ViewHolder(binding.root)

}


interface AdapterClickItemListener<ItemType>  {
    fun onItemClicked(item: ItemType)
}