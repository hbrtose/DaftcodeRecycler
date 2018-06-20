package com.hubose.daftcoderecycler

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var itemListModel: ItemListModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        itemListModel = ViewModelProviders.of(this).get(ItemListModel::class.java)
        createRecyclerView()
        observeState()
    }

    private fun createRecyclerView() {
        rv_items.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))
        rv_items.layoutManager = LinearLayoutManager(this)
        rv_items.adapter = ItemListAdapter(itemListModel, this)
    }

    private fun observeState() {
        itemListModel.getStopped().observe(this, Observer {
            if(it!!) {
                button_stop.visibility = View.GONE
                button_reset.visibility = View.VISIBLE
            } else {
                button_stop.visibility = View.VISIBLE
                button_reset.visibility = View.GONE
            }
        })
    }

    fun start(v: View) {
        itemListModel.start()
    }

    fun stop(v: View) {
        itemListModel.stop()
    }

    fun reset(v: View) {
        itemListModel.reset()
    }
}
