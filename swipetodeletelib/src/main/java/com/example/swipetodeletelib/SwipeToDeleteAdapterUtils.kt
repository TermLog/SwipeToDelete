package com.example.swipetodeletelib

import android.animation.Animator
import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Point
import android.view.View
import android.view.WindowManager

/**
 * Created by AlexZandR on 4/24/17
 */

object SwipeToDeleteAdapterUtils {

    val DELETING_DURATION: Long = 3000
    val PENDING_DURATION = DELETING_DURATION - 150
//    var deviceScreenWidth: Int? = 0
//    fun prepareContactList(count: Int): MutableList<ItemContact> {
//        val result = ArrayList<ItemContact>(count)
//        (0..count - 1).mapTo(result) { ItemContact("User Name " + it.toString(), "+1234567" + it.toString()) }
//        return result
//    }

    private fun deviceWidth(context: Context): Int {
        val wm = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val display = wm.defaultDisplay
        val size = Point()
        display.getSize(size)
        return size.x
//        deviceScreenWidth = size.x
    }

    fun createAnimator(view: View?, options: ModelOptions, context: Context): ValueAnimator {
        return initAnimator(view, options, context, null)
    }

    fun initAnimator(view: View?, options: ModelOptions, context: Context, // TODO improve adding of custom animation to custom view. Maybe I should pass HashMap with animation and View and look over?
                     animator: ValueAnimator?): ValueAnimator { // OR for example pass listener in this method and allow to full customize? It`s better way.
        var animator = animator
        val screenWidth = deviceWidth(context)
        if (animator == null) {
            animator = ValueAnimator.ofFloat(options.posX, screenWidth * options.direction!!.toFloat())
        } else {
            animator.removeAllUpdateListeners()
            animator.removeAllListeners()
            animator.setFloatValues(options.posX, screenWidth * options.direction!!.toFloat())
        }
        animator!!.addUpdateListener { animation ->
            val posX = animation.animatedValue as Float
            view?.x = posX
            options.posX = posX
        }
        animator.addListener(object : Animator.AnimatorListener {
            override fun onAnimationStart(animation: Animator) {
                options.isRunningAnimation = true
                view?.visibility = View.VISIBLE
            }

            override fun onAnimationEnd(animation: Animator) {
                options.isRunningAnimation = false
            }

            override fun onAnimationCancel(animation: Animator) {
                clearOptions(options)
                clearView(view)
            }

            override fun onAnimationRepeat(animation: Animator) {}
        })
        animator.duration = (DELETING_DURATION * (screenWidth - options.posX * options.direction!!.toFloat()) / screenWidth).toLong()
        return animator
    }

    fun clearAnimator(animator: ValueAnimator?) {
        if (animator != null) {
            animator.cancel()
            animator.removeAllUpdateListeners()
            animator.removeAllListeners()
        }
    }

    fun clearOptions(options: ModelOptions?) {
        if (options != null) {
            options.isPendingDelete = false
            options.isRunningAnimation = false
            options.posX = 0f
        }
    }

    fun clearView(view: View?) {
        if (view != null) {
            view.x = 0f
            view.visibility = View.GONE
        }
    }
}