package com.example.swipetodeletelib

import android.support.v7.widget.helper.ItemTouchHelper


class ModelOptions {

    internal var isPendingDelete = false
    internal var isRunningAnimation = false
    internal var direction: Int? = 0
    internal var posX = 0f

    internal fun setDirection(swipeDir: Int) {
        if (ItemTouchHelper.LEFT == swipeDir || ItemTouchHelper.START == swipeDir) direction = LEFT
        else direction = RIGHT

    }

    companion object {

        val LEFT = -1
        val RIGHT = 1
    }
}