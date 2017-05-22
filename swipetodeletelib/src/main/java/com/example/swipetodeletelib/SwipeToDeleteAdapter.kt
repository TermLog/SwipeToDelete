package com.example.swipetodeletelib

import android.animation.ValueAnimator
import android.content.Context
import android.os.Handler
import android.os.Looper
import com.example.swipetodeletelib.SwipeToDeleteAdapterUtils.PENDING_DURATION
import java.lang.IndexOutOfBoundsException
import java.util.*

/**
 * Created by AlexZandR on 4/24/17
 */

class SwipeToDeleteAdapter<K, V, H : SwipeToDeleteHolder<K>>(private val contacts: MutableList<V>, val context: Context) : ItemSwipeListener<K, V>, UndoClickListener<K> {
//    : RecyclerView.Adapter<H>(), ItemSwipeListener, UndoClickListener {

    private val handler = Handler(Looper.getMainLooper())
    private val pendingRemoveActions = HashMap<K, Runnable>(1)
    private val animatorsMap = HashMap<K, ValueAnimator>(1)
    private val modelOptions = HashMap<K, ModelOptions>()

    fun onBindViewHolder(holder: H, key: K, item: V, position: Int) {
        try {
            val contact = contacts[position]
            if (contact == null) {
                contacts.removeAt(position)
//                notifyItemRemoved(position) TODO impl notifyItemRemoved for custom uses
            } else {
                holder.isPendingDelete = modelOptions[key]!!.isPendingDelete
                if (modelOptions[key]!!.isPendingDelete) {
                    onBindPendingContact(holder, key, item)
                } else {
                    onBindCommonContact(holder, key)
                }
            }
        } catch (exc: IndexOutOfBoundsException) {
            exc.printStackTrace()
        }
    }

    fun getItemCount(): Int {
        return contacts.size
    }

    override fun onItemSwiped(swipeDir: Int, key: K, item: V) {
//        val position = holder.adapterPosition
        val options = modelOptions[key]
        if (options!!.isPendingDelete) {
            removeItem(key, item)
        } else {
            modelOptions[key]?.isPendingDelete = true
            modelOptions[key]?.setDirection(swipeDir)
//            notifyItemChanged(position)
        }
    }

    override fun onUndoClick(
            //            position: Int,
            key: K) {
//        if (position != -1) {
//            val mapKey = contacts[position].name
        handler.removeCallbacks(pendingRemoveActions[key])
        modelOptions[key]?.isPendingDelete = false
//            notifyItemChanged(position)
        SwipeToDeleteAdapterUtils.clearAnimator(animatorsMap[key])
//        }
    }

    private fun onBindCommonContact(holder: H, key: K) { // TODO Remove to interface
        clearAnimation(key
//                , holder.progressIndicator // TODO should be progress indicator required in "HolderInterface"?
        )

//        //
//        holder.name.text = contact.name
//        holder.phone.text = contact.phone
//        holder.itemContainer.visibility = View.VISIBLE
//        holder.undoData.visibility = View.GONE
//        //
    }

    private fun onBindPendingContact(holder: H, key: K, item: V
//                                     contact: ItemContact
    ) {
//        //
//        holder.deletedName.text = "You have just deleted " + contact.name
//        holder.itemContainer.visibility = View.GONE
//        holder.undoData.visibility = View.VISIBLE
//        holder.progressIndicator.visibility = View.VISIBLE
//        holder.progressIndicator.x = contact.posX
//        //

        if (pendingRemoveActions[key] == null) {
            pendingRemoveActions.put(key, Runnable { removeItem(key, item) })
        }

        handler.postDelayed(pendingRemoveActions[key], PENDING_DURATION)
        val animator: ValueAnimator?
        if (animatorsMap[key] != null) {
            animator = animatorsMap[key]
            SwipeToDeleteAdapterUtils.initAnimator(
                    //                    holder.progressIndicator
                    null // TODO
                    , modelOptions[key]!!, context, animator)
        } else {
            animator = SwipeToDeleteAdapterUtils.createAnimator(
                    //                    holder.progressIndicator
                    null // TODO
                    , modelOptions[key]!!, context)
            animatorsMap.put(key, animator)
        }
        animator?.start()
    }

    private fun removeItem(key: K, item: V) {
//        val pos = contacts.indexOf(item)
//                .indexOf(contact)
//        handler.removeCallbacks(pendingRemoveActions.remove(contact.name))
//        pendingRemoveActions.remove(contact.name)
        contacts.remove(item)
//                .remove(contact)


//        notifyItemRemoved(pos)

        SwipeToDeleteAdapterUtils.clearOptions(modelOptions[key])
        SwipeToDeleteAdapterUtils.clearAnimator(animatorsMap.remove(key))
    }

    private fun clearAnimation(key: K
//                               , progressView: View
    ) {
        SwipeToDeleteAdapterUtils.clearAnimator(animatorsMap[key])
        SwipeToDeleteAdapterUtils.clearOptions(modelOptions[key])
//        SwipeToDeleteAdapterUtils.clearView(progressView)
    }
}
