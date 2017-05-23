package com.example.swipetodeletelib

/**
 * Created by AlexZandR on 4/24/17
 */

interface ItemSwipeListener<K> {
    fun onItemSwiped(viewHolder: ISwipeToDeleteHolder<K>, swipeDir: Int)
}
