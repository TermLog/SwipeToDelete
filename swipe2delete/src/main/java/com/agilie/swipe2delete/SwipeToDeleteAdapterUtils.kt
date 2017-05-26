package com.agilie.swipe2delete

import android.animation.Animator
import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Point
import android.view.View
import android.view.WindowManager
import com.agilie.swipe2delete.interfaces.IAnimationUpdateListener
import com.agilie.swipe2delete.interfaces.IAnimatorListener

object SwipeToDeleteAdapterUtils {

    private fun deviceWidth(context: Context): Int {
        val wm = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val display = wm.defaultDisplay
        val size = Point()
        display.getSize(size)
        return size.x
    }

    fun initAnimator(options: ModelOptions<*>, context: Context, animatorListener: IAnimatorListener? = null,
                     valUpdateListener: IAnimationUpdateListener? = null, valueAnimator: ValueAnimator? = null): ValueAnimator {
        val animator: ValueAnimator
        val screenWidth = deviceWidth(context)
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

    fun clearAnimator(animator: ValueAnimator?) {
        animator?.cancel()
        animator?.removeAllUpdateListeners()
        animator?.removeAllListeners()
    }

    fun clearOptions(options: ModelOptions<*>?) {
        options?.isPendingDelete = false
        options?.isRunningAnimation = false
        options?.posX = 0f
    }

    fun clearView(view: View?) {
        view?.x = 0f
        view?.visibility = View.GONE
    }
}