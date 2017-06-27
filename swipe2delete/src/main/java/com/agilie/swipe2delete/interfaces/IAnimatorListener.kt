package com.agilie.swipe2delete.interfaces

import android.animation.Animator
import com.agilie.swipe2delete.ModelOptions


interface IAnimatorListener {
    fun onAnimationEnd(animation: Animator?, options: ModelOptions<*>) {}

    fun onAnimationCancel(animation: Animator?, options: ModelOptions<*>) {}

    fun onAnimationStart(animation: Animator?, options: ModelOptions<*>) {}

    fun onAnimationRepeat(animation: Animator, options: ModelOptions<*>) {}
}