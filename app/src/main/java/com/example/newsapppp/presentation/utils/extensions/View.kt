package com.example.newsapppp.presentation.utils.extensions

import android.view.View
import android.view.animation.Animation

internal fun View.invisible() {
    visibility = View.INVISIBLE
}

internal fun View.visible() {
    visibility = View.VISIBLE
}

internal fun View.startAnim(animation: Animation, onEnd: () -> Unit = {}) {
    animation.setAnimationListener(object : Animation.AnimationListener {
        override fun onAnimationStart(animation: Animation?) = Unit

        override fun onAnimationEnd(animation: Animation?) {
            onEnd()
        }

        override fun onAnimationRepeat(animation: Animation?) = Unit
    })
    startAnimation(animation)
}
