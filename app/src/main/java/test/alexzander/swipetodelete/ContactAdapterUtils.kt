package test.alexzander.swipetodelete

import android.animation.Animator
import android.animation.ValueAnimator
import android.view.View

import java.util.ArrayList

/**
 * Created by AlexZandR on 4/24/17
 */

object ContactAdapterUtils {

    val DELETING_DURATION: Long = 3000
    val PENDING_DURATION = DELETING_DURATION - 150

    fun prepareContactList(count: Int): MutableList<ItemContact> {
        val result = ArrayList<ItemContact>(count)
        (0..count - 1).mapTo(result) { ItemContact("User Name " + it.toString(), "+1234567" + it.toString()) }
        return result
    }

    fun createAnimator(view: View, contact: ItemContact): ValueAnimator {
        return initAnimator(view, contact, null)
    }

    fun initAnimator(view: View, contact: ItemContact,
                     animator: ValueAnimator?): ValueAnimator {
        var animator = animator
        val screenWidth = MainActivity.sDeviceScreenWidth.toFloat()
        if (animator == null) {
            animator = ValueAnimator.ofFloat(contact.posX, screenWidth * contact.direction!!)
        } else {
            animator.removeAllUpdateListeners()
            animator.removeAllListeners()
            animator.setFloatValues(contact.posX, screenWidth * contact.direction!!)
        }
        animator!!.addUpdateListener { animation ->
            val posX = animation.animatedValue as Float
            view.x = posX
            contact.posX = posX
        }
        animator.addListener(object : Animator.AnimatorListener {
            override fun onAnimationStart(animation: Animator) {
                contact.isRunningAnimation = true
                view.visibility = View.VISIBLE
            }

            override fun onAnimationEnd(animation: Animator) {
                contact.isRunningAnimation = false
            }

            override fun onAnimationCancel(animation: Animator) {
                clearContact(contact)
                clearView(view)
            }

            override fun onAnimationRepeat(animation: Animator) {}
        })
        animator.duration = (DELETING_DURATION * (screenWidth - contact.posX.toFloat() * contact.direction!!.toFloat()) / screenWidth).toLong()
        return animator
    }

    fun clearAnimator(animator: ValueAnimator?) {
        if (animator != null) {
            animator.cancel()
            animator.removeAllUpdateListeners()
            animator.removeAllListeners()
        }
    }

    fun clearContact(contact: ItemContact?) {
        if (contact != null) {
            contact.isPendingDelete = false
            contact.isRunningAnimation = false
            contact.posX = 0f
        }
    }

    fun clearView(view: View?) {
        if (view != null) {
            view.x = 0f
            view.visibility = View.GONE
        }
    }
}