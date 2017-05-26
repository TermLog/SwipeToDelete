package com.agilie.swipe2delete

import android.animation.ValueAnimator
import android.view.View

fun clearOptions(options: ModelOptions<*>?) {
    options?.isPendingDelete = false
    options?.isRunningAnimation = false
    options?.posX = 0f
}

fun clearAnimator(animator: ValueAnimator?) {
    animator?.cancel()
    animator?.removeAllUpdateListeners()
    animator?.removeAllListeners()
}

fun clearView(view: View?) {
    view?.x = 0f
    view?.visibility = View.GONE
}

fun getRowWidth(view: View?) {
    rowWidth = view?.measuredWidth ?: 0
    view?.addOnLayoutChangeListener { v, left, top, right, bottom, oldLeft, oldTop, oldRight, oldBottom -> rowWidth = right - left }
}

