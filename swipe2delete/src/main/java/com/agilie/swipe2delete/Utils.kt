package com.agilie.swipe2delete

import android.animation.ValueAnimator
import android.view.View

internal fun clearOptions(options: ModelOptions<*>?) {
    options?.pendingDelete = false
    options?.runningAnimation = false
    options?.posX = 0f
}

internal fun clearAnimator(animator: ValueAnimator?) {
    animator?.cancel()
    animator?.removeAllUpdateListeners()
    animator?.removeAllListeners()
}

internal fun clearView(view: View?) {
    view?.x = 0f
    view?.visibility = View.GONE
}

internal fun getRowWidth(view: View?) {
    rowWidth = view?.measuredWidth ?: 0
    view?.addOnLayoutChangeListener { v, left, top, right, bottom, oldLeft, oldTop, oldRight, oldBottom -> rowWidth = right - left }
}

