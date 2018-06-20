package com.hubose.daftcoderecycler

import android.arch.lifecycle.LifecycleOwner
import android.arch.lifecycle.Observer
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.item_list.view.*

class ItemListAdapter(model: ItemListModel, owner: LifecycleOwner): RecyclerView.Adapter<ItemListAdapter.ItemHolder>() {

    private val items = mutableListOf<Item>()

    init {
        model.getItems().observe(owner, Observer {
            items.clear()
            items.addAll(it!!)
            notifyDataSetChanged()
        })
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_list, parent, false)
        return ItemHolder(view)
    }

    override fun onBindViewHolder(holder: ItemHolder, position: Int) {
        holder.bind(items[position])
    }

    class ItemHolder(private val v: View): RecyclerView.ViewHolder(v) {
        fun bind(item: Item) {
            v.tv_count.text = item.displayCount().toString()
            v.iv_circle.setColorFilter(item.getColor())
        }
    }
}