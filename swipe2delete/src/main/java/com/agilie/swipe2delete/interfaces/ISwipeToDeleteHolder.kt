package com.agilie.swipe2delete.interfaces

import android.view.View

interface ISwipeToDeleteHolder<K> {

    var isPendingDelete: Boolean
    /**
     *  get() =
     *  if (isPendingDelete!!) undoContainer
     *  else itemContainer
     *
     */
    val topContainer: View

    var key: K
}