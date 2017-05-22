package com.example.swipetodeletelib

/**
 * Created by AlexZandR on 4/24/17
 */

interface ItemSwipeListener<K, V> {
    fun onItemSwiped(swipeDir: Int, key: K, item: V)
}
