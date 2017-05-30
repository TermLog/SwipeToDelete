package com.agilie.swipe2delete

import android.animation.ValueAnimator
import android.os.Handler
import android.os.Looper
import android.view.View
import com.agilie.swipe2delete.interfaces.*
import java.lang.IndexOutOfBoundsException

class SwipeToDeleteDelegate<K, in V, H : ISwipeToDeleteHolder<K>>(private val items: MutableList<V>, val swipeToDeleteAdapter: ISwipeToDeleteAdapter<K, V, H>) : ItemSwipeListener<K>, IUndoClickListener<K> {
    var deletingDuration: Long? = null
    val itemTouchCallBack = ContactItemTouchCallback(this)
    val handler = Handler(Looper.getMainLooper())
    val pendingRemoveActions = HashMap<K, Runnable?>(1)
    val animatorsMap = HashMap<K, ValueAnimator>(1)
    val modelOptions = HashMap<K, ModelOptions<K>>()
    val holders = HashMap<K, H>()
    var animationUpdateListener: IAnimationUpdateListener? = null
    var animatorListener: IAnimatorListener? = null
    var isKnownWidth = false

    init {
        if (swipeToDeleteAdapter is IAnimatorListener) animatorListener = swipeToDeleteAdapter
        if (swipeToDeleteAdapter is IAnimationUpdateListener) animationUpdateListener = swipeToDeleteAdapter
    }

    fun onBindViewHolder(holder: H, key: K, position: Int) {
        try {
            if (!isKnownWidth) {
                getRowWidth(holder.topContainer)
                isKnownWidth = true
            }
            holder.key = key
            if (!modelOptions.containsKey(key)) modelOptions.put(key, ModelOptions(key, deletingDuration ?: 0))
            val item = items[position]
            if (item == null) {
                items.removeAt(position)
                swipeToDeleteAdapter.notifyItemRemoved(position)
            } else {
                holders[key] = holder
                holder.isPendingDelete = modelOptions[key]!!.isPendingDelete

                if (modelOptions[key]!!.isPendingDelete) onBindPendingContact(holder, key, item, animatorListener, animationUpdateListener, position)
                else onBindCommonContact(holder, key, item, position)
            }
        } catch (exc: IndexOutOfBoundsException) {
            exc.printStackTrace()
        }
    }

    override fun onItemSwiped(viewHolder: ISwipeToDeleteHolder<K>, swipeDir: Int) {
        val key = viewHolder.key
        val modelOption = modelOptions[key]
        if (modelOption?.isPendingDelete ?: false) removeItemByKey(key)
        else {
            modelOption?.isPendingDelete = true
            modelOption?.setDirection(swipeDir)
            swipeToDeleteAdapter.notifyItemChanged(swipeToDeleteAdapter.findItemPositionByKey(key))
        }
    }

    override fun onUndo(key: K) {
        val position = swipeToDeleteAdapter.findItemPositionByKey(key)
        handler.removeCallbacks(pendingRemoveActions[key])
        modelOptions[key]?.isPendingDelete = false
        swipeToDeleteAdapter.notifyItemChanged(position)
        clearAnimator(animatorsMap[key])
    }
    fun onBindCommonContact(holder: H, key: K, item: V, position: Int) {
        swipeToDeleteAdapter.onBindCommonItem(holder, key, item, position)

    }

    fun onBindPendingContact(holder: H, key: K, item: V, IAnimatorListener: IAnimatorListener? = null, IAnimationUpdateListener: IAnimationUpdateListener? = null, position: Int) {
        swipeToDeleteAdapter.onBindPendingItem(holder, key, item, position)
        pendingRemoveActions[key] ?: pendingRemoveActions.put(key, Runnable { removeItemByKey(key) })
        handler.postDelayed(pendingRemoveActions[key], modelOptions[key]!!.pendingDuration)
        val animator: ValueAnimator?
        if (animatorsMap[key] != null) {
            animator = animatorsMap[key]
            initAnimator(modelOptions[key]!!, IAnimatorListener, IAnimationUpdateListener, animator)
        } else {
            animator = initAnimator(modelOptions[key]!!, IAnimatorListener, IAnimationUpdateListener)
            animatorsMap.put(key, animator)
        }
        animator?.start()
    }

    fun removeItemByKey(key: K) {
        swipeToDeleteAdapter.removeItem(key)
    }

    fun removeItem(key: K){
        val position = swipeToDeleteAdapter.findItemPositionByKey(key)
        removeItemFromList(key, items.removeAt(position), position)
    }

    fun removeItemFromList(key: K, item: V, position: Int) {
        handler.removeCallbacks(pendingRemoveActions.remove(key))
        pendingRemoveActions.remove(key)
        items.remove(item)
        holders.remove(key)
        modelOptions.remove(key)
        swipeToDeleteAdapter.notifyItemRemoved(position)
        clearOptions(modelOptions[key])
        clearAnimator(animatorsMap.remove(key))
        swipeToDeleteAdapter.onItemDeleted(item)
    }

    fun clearAnimation(key: K, view: View?) {
        clearAnimator(animatorsMap[key])
        clearOptions(modelOptions[key])
        clearView(view)
    }
}