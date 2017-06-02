package com.agilie.swipe2delete.interfaces

import android.view.View

interface ISwipeToDeleteHolder<K> {

    var pendingDelete: Boolean
    /**
     *  get() =
     *  if (pendingDelete!!) undoContainer
     *  else itemContainer
     *
     */
    val topContainer: View

    var key: K
}