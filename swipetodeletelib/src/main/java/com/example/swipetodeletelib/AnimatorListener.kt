package com.example.swipetodeletelib

import android.animation.Animator


interface AnimatorListener {
    fun onAnimationEnd(animation: Animator?, options: ModelOptions<*>)

    fun onAnimationCancel(animation: Animator?, options: ModelOptions<*>)

    fun onAnimationStart(animation: Animator?, options: ModelOptions<*>)

    fun onAnimationRepeat(animation: Animator, options: ModelOptions<*>)
}