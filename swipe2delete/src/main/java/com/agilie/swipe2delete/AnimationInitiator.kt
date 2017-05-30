package com.agilie.swipe2delete

import android.animation.Animator
import android.animation.ValueAnimator
import android.support.v7.widget.RecyclerView
import com.agilie.swipe2delete.interfaces.IAnimationUpdateListener
import com.agilie.swipe2delete.interfaces.IAnimatorListener

var rowWidth: Int = 0

fun initAnimator(options: ModelOptions<*>, animatorListener: IAnimatorListener? = null,
                 valUpdateListener: IAnimationUpdateListener? = null, valueAnimator: ValueAnimator? = null): ValueAnimator {
    val animator: ValueAnimator
    val screenWidth = rowWidth

    if (valueAnimator == null) animator = ValueAnimator.ofFloat(options.posX, screenWidth * options.direction!!.toFloat())
    else {
        animator = valueAnimator
        animator.removeAllUpdateListeners()
        animator.removeAllListeners()
        animator.setFloatValues(options.posX, screenWidth * options.direction!!.toFloat())
    }

    animator.addUpdateListener { animation -> valUpdateListener?.onAnimationUpdate(animation, options) }
    animator.addListener((object : Animator.AnimatorListener {
        override fun onAnimationStart(animation: Animator) {
            options.isRunningAnimation = true
            animatorListener?.onAnimationStart(animation, options)
        }

        override fun onAnimationEnd(animation: Animator) {
            options.isRunningAnimation = false
            animatorListener?.onAnimationEnd(animation, options)
        }

        override fun onAnimationCancel(animation: Animator) {
            clearOptions(options)
            animatorListener?.onAnimationCancel(animation, options)
        }

        override fun onAnimationRepeat(animation: Animator) {
            animatorListener?.onAnimationRepeat(animation, options)
        }
    }))
    animator.duration = (options.deletingDuration * (screenWidth - options.posX * options.direction!!.toFloat()) / screenWidth).toLong()
    return animator
}