package com.example.swipetodeletelib

import android.view.View


/**
 * Created by AlexZandR on 4/24/17.
 */

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