package com.example.swipetodeletelib


interface ISwipeToDeleteAdapter<K, in V, in H> {

    val animatorListener: AnimatorListener

    fun notifyItemRemoved(position: Int)

    fun notifyItemChanged(position: Int)

    fun findPositionByKey(key: K) : Int

    fun onBindCommonContact(holder: H?, key: K, item: V)

    fun onBindPendingContact(holder: H?, key: K, item: V)
}