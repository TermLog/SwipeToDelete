package com.agilie.swipe2delete

import android.support.v7.widget.helper.ItemTouchHelper

class ModelOptions<K>(var key: K, var deletingDuration: Long) {
    val pendingDuration: Long
        get() =
        if ((deletingDuration - 150) <= 0) 150
        else deletingDuration - 150
    var isPendingDelete = false
    var isRunningAnimation = false
    var direction: Int? = 0
    var posX = 0f
    var isViewActive = false

    internal fun setDirection(swipeDir: Int) =
            if (ItemTouchHelper.LEFT == swipeDir || ItemTouchHelper.START == swipeDir) direction = LEFT
            else direction = RIGHT


    companion object {
        val LEFT = -1
        val RIGHT = 1
    }
}