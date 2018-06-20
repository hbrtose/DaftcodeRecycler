package com.hubose.daftcoderecycler

import android.graphics.Color
import java.util.*

class Item {

    private var count: Int
    private val color: Int

    init {
        count = 0
        val randGen = Random()
        color = if(randGen.nextBoolean()) Color.RED else Color.BLUE
    }

    fun displayCount(): Int {
        return when(color) {
            Color.BLUE -> count * 3
            else -> count
        }
    }

    fun addToCount(i: Int) {
        count += i
    }

    fun increment() {
        addToCount(1)
    }

    fun getColor(): Int {
        return color
    }

    fun reset() {
        count = 0
    }

    fun getValue(): Int {
        return count
    }
}