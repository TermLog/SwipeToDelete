package test.alexzander.swipetodelete

import android.animation.ValueAnimator
import android.os.Handler
import android.os.Looper
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import java.util.HashMap

import test.alexzander.swipetodelete.ContactAdapterUtils.PENDING_DURATION

/**
 * Created by AlexZandR on 4/24/17
 */

class ContactAdapter(private val contacts: MutableList<ItemContact>) : RecyclerView.Adapter<ContactHolder>(), ItemSwipeListener, UndoClickListener {

    private val handler = Handler(Looper.getMainLooper())
    private val pendingRemoveActions = HashMap<String, Runnable>(1)
    private val animatorsMap = HashMap<String, ValueAnimator>(1)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactHolder {
        val view = LayoutInflater.from(parent.context).inflate(
                R.layout.list_item, parent, false)
        return ContactHolder(view, this)
    }

    override fun onBindViewHolder(holder: ContactHolder, position: Int) {
        try {
            val contact = contacts[position]
            if (contact == null) {
                contacts.removeAt(position)
                notifyItemRemoved(position)
            } else {
                holder.isPendingDelete = contact.isPendingDelete
                if (contact.isPendingDelete) {
                    onBindPendingContact(holder, contact)
                } else {
                    onBindCommonContact(holder, contact)
                }
            }
        } catch (exc: IndexOutOfBoundsException) {
            exc.printStackTrace()
        }

    }

    override fun getItemCount(): Int {
        return contacts.size
    }

    override fun onItemSwiped(holder: ContactHolder, swipeDir: Int) {
        val position = holder.adapterPosition
        val contact = contacts[position]
        if (contact.isPendingDelete) {
            removeItem(contact)
        } else {
            contact.isPendingDelete = true
            contact.setDirection(swipeDir)
            notifyItemChanged(position)
        }
    }

    override fun onUndoClick(position: Int) {
        if (position != -1) {
            val mapKey = contacts[position].name
            handler.removeCallbacks(pendingRemoveActions[mapKey])
            contacts[position].isPendingDelete = false
            notifyItemChanged(position)
            ContactAdapterUtils.clearAnimator(animatorsMap[mapKey])
        }
    }

    private fun onBindCommonContact(holder: ContactHolder, contact: ItemContact) {
        clearAnimation(contact, holder.progressIndicator)
        holder.name.text = contact.name
        holder.phone.text = contact.phone
        holder.contactContainer.visibility = View.VISIBLE
        holder.undoData.visibility = View.GONE
    }

    private fun onBindPendingContact(holder: ContactHolder, contact: ItemContact) {
        holder.deletedName.text = "You have just deleted " + contact.name
        holder.contactContainer.visibility = View.GONE
        holder.undoData.visibility = View.VISIBLE
        holder.progressIndicator.visibility = View.VISIBLE
        holder.progressIndicator.x = contact.posX

        if (pendingRemoveActions[contact.name] == null) {
            pendingRemoveActions.put(contact.name, Runnable { removeItem(contact) })
        }

        handler.postDelayed(pendingRemoveActions[contact.name], PENDING_DURATION)
        val animator: ValueAnimator?
        if (animatorsMap[contact.name] != null) {
            animator = animatorsMap[contact.name]
            ContactAdapterUtils.initAnimator(holder.progressIndicator, contact, animator)
        } else {
            animator = ContactAdapterUtils.createAnimator(holder.progressIndicator, contact)
            animatorsMap.put(contact.name, animator)
        }
        animator?.start()
    }

    private fun removeItem(contact: ItemContact) {
        val pos = contacts.indexOf(contact)
        handler.removeCallbacks(pendingRemoveActions.remove(contact.name))
        pendingRemoveActions.remove(contact.name)
        contacts.remove(contact)
        notifyItemRemoved(pos)
        ContactAdapterUtils.clearContact(contact)
        ContactAdapterUtils.clearAnimator(animatorsMap.remove(contact.name))
    }

    private fun clearAnimation(contact: ItemContact, progressView: View) {
        ContactAdapterUtils.clearAnimator(animatorsMap[contact.name])
        ContactAdapterUtils.clearContact(contact)
        ContactAdapterUtils.clearView(progressView)
    }
}
