package com.example.swipetodeletelib.interfaces


interface IAnimationUpdateListener {
    fun onAnimationUpdate(animation: android.animation.ValueAnimator?, options: com.example.swipetodeletelib.ModelOptions<*>)
}