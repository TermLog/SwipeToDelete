package com.agilie.swipe2delete.interfaces

import com.agilie.swipe2delete.ModelOptions


interface IAnimationUpdateListener {
    fun onAnimationUpdated(animation: android.animation.ValueAnimator?, options: ModelOptions<*>)
}