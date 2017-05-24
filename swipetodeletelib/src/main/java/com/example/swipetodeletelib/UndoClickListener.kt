package com.example.swipetodeletelib

/**
 * Created by AlexZandR on 4/24/17
 */

interface UndoClickListener<K> {
    fun onUndo(key: K)
}
