package com.example.swipetodeletelib


interface ISwipeToDeleteAdapter<K, in V, in H> {

    fun notifyItemRemoved(position: Int)

    fun notifyItemChanged(position: Int)

    fun findItemPositionByKey(key: K) : Int

    fun onBindCommonItem(holder: H, key: K, item: V)

    fun onBindPendingItem(holder: H, key: K, item: V)

    fun onItemDeleted(item: V) {}

    fun removeItem(key: K, item: V)
}