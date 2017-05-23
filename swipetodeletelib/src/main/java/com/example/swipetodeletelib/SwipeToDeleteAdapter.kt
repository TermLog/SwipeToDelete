package com.example.swipetodeletelib

import android.animation.ValueAnimator
import android.content.Context
import android.os.Handler
import android.os.Looper
import android.view.View
import com.example.swipetodeletelib.SwipeToDeleteAdapterUtils.PENDING_DURATION
import java.lang.IndexOutOfBoundsException

class SwipeToDeleteAdapter<K, in V, H : ISwipeToDeleteHolder<K>>(private val items: MutableList<V>,
                                                                    val context: Context, val swipeToDeleteAdapter: ISwipeToDeleteAdapter<K, V, H>) : ItemSwipeListener<K>, UndoClickListener<K> {
    private val handler = Handler(Looper.getMainLooper())
    private val pendingRemoveActions = HashMap<K, Runnable>(1)
    private val animatorsMap = HashMap<K, ValueAnimator>(1)
    private val modelOptions = HashMap<K, ModelOptions<K>>()
    val holders = HashMap<K, H>()
    fun onBindViewHolder(holder: H?, key: K, position: Int) {
        try {
            modelOptions.put(key, ModelOptions(key))
            val item = items[position]
            if (item == null) {
                items.removeAt(position)
                swipeToDeleteAdapter.notifyItemRemoved(position)
            } else {
                holders[key] = holder!!
                holder.isPendingDelete = modelOptions[key]!!.isPendingDelete
                if (modelOptions[key]!!.isPendingDelete) {
                    onBindPendingContact(holder, key, item, swipeToDeleteAdapter.animatorListener)
                } else {
                    onBindCommonContact(holder, key, item)
                }
            }
        } catch (exc: IndexOutOfBoundsException) {
            exc.printStackTrace()
        }
    }

    override fun onItemSwiped(viewHolder: ISwipeToDeleteHolder<K>, swipeDir: Int) {
        val key = viewHolder.key
        val position = swipeToDeleteAdapter.findPositionByKey(key)
        val item = items[position]
        val options = modelOptions[key]
        if (options!!.isPendingDelete) {
            removeItem(key, item, position)
        } else {
            modelOptions[key]?.isPendingDelete = true
            modelOptions[key]?.setDirection(swipeDir)
            swipeToDeleteAdapter.notifyItemChanged(position)
        }
    }

    override fun onUndoClick(key: K) {
        val position = swipeToDeleteAdapter.findPositionByKey(key)
        handler.removeCallbacks(pendingRemoveActions[key])
        modelOptions[key]?.isPendingDelete = false
        swipeToDeleteAdapter.notifyItemChanged(position)
        SwipeToDeleteAdapterUtils.clearAnimator(animatorsMap[key])
    }

    private fun onBindCommonContact(holder: H?, key: K, item: V) {
        swipeToDeleteAdapter.onBindCommonContact(holder, key, item)
    }

    private fun onBindPendingContact(holder: H?, key: K, item: V, animatorListener: AnimatorListener, animationUpdateListener: AnimationUpdateListener? = null) {
        swipeToDeleteAdapter.onBindPendingContact(holder, key, item)
        if (pendingRemoveActions[key] == null) {
            pendingRemoveActions.put(key, Runnable { removeItem(key, item, swipeToDeleteAdapter.findPositionByKey(key)) })
        }
        handler.postDelayed(pendingRemoveActions[key], PENDING_DURATION)
        val animator: ValueAnimator?
        if (animatorsMap[key] != null) {
            animator = animatorsMap[key]
            SwipeToDeleteAdapterUtils.initAnimator(modelOptions[key]!!, context, animatorListener, animationUpdateListener, animator)
        } else {
            animator = SwipeToDeleteAdapterUtils.initAnimator(modelOptions[key]!!, context, animatorListener, animationUpdateListener)
            animatorsMap.put(key, animator)
        }
        animator?.start()
    }

    private fun removeItem(key: K, item: V, position: Int) {
        handler.removeCallbacks(pendingRemoveActions.remove(key))
        pendingRemoveActions.remove(key)
        items.remove(item)
        holders.remove(key)
        modelOptions.remove(key)
        swipeToDeleteAdapter.notifyItemRemoved(position)
        SwipeToDeleteAdapterUtils.clearOptions(modelOptions[key])
        SwipeToDeleteAdapterUtils.clearAnimator(animatorsMap.remove(key))
    }

    private fun clearAnimation(key: K, view: View?) {
        SwipeToDeleteAdapterUtils.clearAnimator(animatorsMap[key])
        SwipeToDeleteAdapterUtils.clearOptions(modelOptions[key])
        SwipeToDeleteAdapterUtils.clearView(view)
    }
}