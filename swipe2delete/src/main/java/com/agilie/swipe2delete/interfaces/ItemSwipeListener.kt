package com.agilie.swipe2delete.interfaces

interface ItemSwipeListener<K> {
    fun onItemSwiped(viewHolder: ISwipeToDeleteHolder<K>, swipeDir: Int)

    fun clearView(viewHolder: ISwipeToDeleteHolder<K>)
}
