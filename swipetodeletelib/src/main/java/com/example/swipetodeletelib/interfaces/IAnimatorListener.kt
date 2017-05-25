package com.example.swipetodeletelib.interfaces

import android.animation.Animator
import com.example.swipetodeletelib.ModelOptions


interface IAnimatorListener {
    fun onAnimationEnd(animation: Animator?, options: ModelOptions<*>) {}

    fun onAnimationCancel(animation: Animator?, options: ModelOptions<*>) {}

    fun onAnimationStart(animation: Animator?, options: ModelOptions<*>) {}

    fun onAnimationRepeat(animation: Animator, options: ModelOptions<*>) {}
}