package com.hubose.daftcoderecycler

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import java.util.*
import java.util.concurrent.TimeUnit

class ItemListModel: ViewModel() {

    private val interval: Long = 1
    private val pool = 100

    private val itemList = mutableListOf<Item>()
    private val items = MutableLiveData<List<Item>>()
    private val stopped = MutableLiveData<Boolean>()
    private var timer: Disposable? = null

    fun start() {
        if(timer?.isDisposed != false) {
            classifyAction(Random().nextInt(100))
            items.value = itemList
            stopped.value = false
            timer = Observable.interval(interval, TimeUnit.SECONDS)
                    .repeatUntil {
                        stopped.value!!
                    }
                    .subscribeOn(Schedulers.computation())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe {
                        classifyAction(Random().nextInt(pool))
                        items.value = itemList
                    }
        }
    }

    fun stop() {
        if(itemList.size > 0) {
            stopped.value = true
            timer?.dispose()
        }
    }

    fun reset() {
        stopped.value = false
        itemList.clear()
        items.value = itemList
    }

    fun getItems(): LiveData<List<Item>> {
        return items
    }

    fun getStopped(): LiveData<Boolean> {
        return stopped
    }

    private fun classifyAction(i: Int) {
        if(itemList.size < 5) {
            addItem()
        } else {
            when (i) {
                in 0..49 -> incrementRandomItem()
                in 50..84 -> resetRandomItem()
                in 85..94 -> deleteRandomItem()
                else -> addPreviousToRandomItem()
            }
        }
    }

    private fun addItem() {
        itemList.add(Item())
    }

    private fun incrementRandomItem() {
        itemList[randomItem()].increment()
    }

    private fun resetRandomItem() {
        itemList[randomItem()].reset()
    }

    private fun deleteRandomItem() {
        itemList.removeAt(randomItem())
    }

    private fun addPreviousToRandomItem() {
        val index = randomItem()
        val previous = when(index) {
            0 -> 4
            else -> index - 1
        }
        itemList[index].addToCount(itemList[previous].getValue())
    }

    private fun randomItem(): Int {
        return Random().nextInt(itemList.size)
    }
}