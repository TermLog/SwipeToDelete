package com.agilie.swipe2delete

import android.support.v7.widget.helper.ItemTouchHelper

class ModelOptions<K>(var key: K, var deletingDuration: Long) {
    val pendingDuration: Long
        get() =
        if ((deletingDuration - 150) <= 0) 150
        else deletingDuration - 150
    var direction: Int? = 0
    var posX = 0f

    var pendingDelete = false
    var runningAnimation = false
    var viewActive = false

    internal fun setDirection(swipeDir: Int) =
            if (ItemTouchHelper.LEFT == swipeDir || ItemTouchHelper.START == swipeDir) direction = LEFT
            else direction = RIGHT

    companion object {
        val LEFT = -1
        val RIGHT = 1
    }
}