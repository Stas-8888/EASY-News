package com.example.newsapppp.presentation.extensions

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.core.animation.doOnEnd
import com.example.newsapppp.R

internal fun View.invisible() {
    visibility = View.INVISIBLE
}

internal fun View.visible() {
    visibility = View.VISIBLE
}

internal fun View.visibility(data: Boolean) {
    visibility = if (data) View.VISIBLE else View.INVISIBLE
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

fun View.slideDown() {
    val height = this.height
    ObjectAnimator.ofFloat(this, View.TRANSLATION_Y, 0f, height.toFloat()).apply {
        duration = 1000
        start()
    }
}

fun View.slideUp() {
    val height = this.height
    ObjectAnimator.ofFloat(this, View.TRANSLATION_Y, height.toFloat(), 0f).apply {
        duration = 1000
        start()
    }
}


fun View.shake() = ObjectAnimator.ofFloat(this, "translationX", 0F, -15F, 15F, -10F, 10F, -5F, 5F, 0F)
    .setDuration(500)
    .start()

fun View.fadeIn(): ObjectAnimator = ObjectAnimator.ofFloat(this, View.ALPHA, 0f, 1f).apply {
    duration = 1000
    start()
}

fun View.bump(action: () -> Unit = {}) {
    val x = ObjectAnimator.ofFloat(this, "scaleX", 1F, 1.1F, 1F)
    val y = ObjectAnimator.ofFloat(this, "scaleY", 1F, 1.1F, 1F)

    AnimatorSet().apply {
        playTogether(x, y)
        duration = 250
        doOnEnd { action() }
        start()
    }
}

fun View.clickAnim(){
    startAnimation(AnimationUtils.loadAnimation(context, R.anim.fab_explode))
}
