package com.example.swipetodeletelib


interface ISwipeToDeleteAdapter<K, in V, in H> {

    val animatorListener: AnimatorListener?

    val animationUpdateListener: AnimationUpdateListener?

    fun notifyItemRemoved(position: Int)

    fun notifyItemChanged(position: Int)

    fun findItemPositionByKey(key: K) : Int

    fun onBindCommonItem(holder: H, key: K, item: V)

    fun onBindPendingItem(holder: H, key: K, item: V)

    fun deleteAction(item: V) : Boolean

    fun onItemDeleted(item: V)

    fun onDeleteFailed(item: V)
}