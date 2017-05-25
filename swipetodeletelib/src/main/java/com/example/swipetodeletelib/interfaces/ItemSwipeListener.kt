package com.example.swipetodeletelib.interfaces

interface ItemSwipeListener<K> {
    fun onItemSwiped(viewHolder: ISwipeToDeleteHolder<K>, swipeDir: Int)
}
