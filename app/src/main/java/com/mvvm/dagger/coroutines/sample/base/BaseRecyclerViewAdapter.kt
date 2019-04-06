package com.mvvm.dagger.coroutines.sample.base

import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import android.view.ViewGroup
import com.mvvm.dagger.coroutines.sample.BR
import android.view.LayoutInflater
import androidx.recyclerview.widget.RecyclerView

abstract class BaseRecyclerViewAdapter(private val list: List<Any>?, private val listener: BaseRecyclerViewAdapter.OnItemClickListener? = null): RecyclerView.Adapter<BaseRecyclerViewAdapter.BaseViewHolder>() {

    abstract fun getItemLayoutId(): Int

    override fun getItemViewType(position: Int): Int = getItemLayoutId()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        val dataBinding = DataBindingUtil.inflate<ViewDataBinding>(LayoutInflater.from(parent.context), viewType, parent, false)
        return BaseViewHolder(dataBinding,listener)
    }

    override fun getItemCount(): Int = list?.size ?: 0

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) = holder.bind(list?.get(position))

    class BaseViewHolder(private val dataBinding: ViewDataBinding, private val listener: BaseRecyclerViewAdapter.OnItemClickListener? = null) : RecyclerView.ViewHolder(dataBinding.root) {
        fun bind(item: Any?) {
            dataBinding.root.setOnClickListener { listener?.onItemClicked(item) }
            dataBinding.setVariable(BR.item,item)
            dataBinding.executePendingBindings()
        }
    }

    interface OnItemClickListener {
        fun onItemClicked(item: Any?)
    }

}