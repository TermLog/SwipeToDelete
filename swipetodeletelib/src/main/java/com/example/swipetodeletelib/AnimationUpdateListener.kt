package com.example.swipetodeletelib

import android.animation.ValueAnimator


interface AnimationUpdateListener {
    fun onAnimationUpdate(animation: ValueAnimator?, options: ModelOptions<*>)
}