package com.example.newsapppp.presentation.extensions

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.content.Context
import android.view.View
import android.view.animation.AccelerateInterpolator
import android.view.animation.AnimationUtils
import android.view.animation.DecelerateInterpolator
import android.view.inputmethod.InputMethodManager
import androidx.core.animation.doOnEnd
import androidx.interpolator.view.animation.LinearOutSlowInInterpolator
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

fun View.shake() =
    ObjectAnimator.ofFloat(this, "translationX", 0F, -15F, 15F, -10F, 10F, -5F, 5F, 0F)
        .setDuration(500)
        .start()

fun View.fadeIn(): ObjectAnimator = ObjectAnimator.ofFloat(this, View.ALPHA, 0f, 1f).apply {
    duration = 250
    start()
}

fun View.fadeOut(): ObjectAnimator = ObjectAnimator.ofFloat(this, View.ALPHA, 1f, 0f).apply {
    duration = 250
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

fun View.clickAnim() {
    startAnimation(AnimationUtils.loadAnimation(context, R.anim.fab_explode))
}

fun View.showKeyboard() {
    val inputMethodManager = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    requestFocus()
    inputMethodManager.showSoftInput(this, 0)
}

fun View.hideKeyboard() {
    (context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager).apply {
        hideSoftInputFromWindow(windowToken, 0)
    }
}

fun View.fadeOutAnimation(duration: Long = 300, visibility: Int = View.INVISIBLE, completion: (() -> Unit)? = null) {
    animate()
        .alpha(0f)
        .setDuration(duration)
        .withEndAction {
            this.visibility = visibility
            completion?.let {
                it()
            }
        }
}

fun View.fadeInAnimation(duration: Long = 300, completion: (() -> Unit)? = null) {
    alpha = 0f
    visibility = View.VISIBLE
    animate()
        .alpha(1f)
        .setDuration(duration)
        .withEndAction {
            completion?.let {
                it()
            }
        }
}

fun View.slideOutAnimation(duration: Long = 300, delay: Long = 0L, visibility: Int = View.INVISIBLE, completion: (() -> Unit)? = null) {
    animate()
        .translationX(-50F)
        .alpha(0f)
        .setDuration(duration)
        .setStartDelay(delay)
        .setInterpolator(AccelerateInterpolator())
        .withEndAction {
            this.visibility = visibility
            completion?.let {
                it()
            }
        }
}

fun View.slideInAnimation(duration: Long = 300, delay: Long = 0L, completion: (() -> Unit)? = null) {
    translationX = 50F
    visibility = View.VISIBLE
    animate()
        .translationX(0f)
        .alpha(1f)
        .setDuration(duration)
        .setStartDelay(delay)
        .setInterpolator(DecelerateInterpolator())
        .withEndAction {
            completion?.let {
                it()
            }
        }
}

fun View.animateElevation(elevation: Float): ValueAnimator? {
    val valueAnimator = ValueAnimator.ofFloat(0F, elevation)
    valueAnimator.interpolator = LinearOutSlowInInterpolator()
    valueAnimator.duration = 5000
    valueAnimator.addUpdateListener { animation ->
        this.elevation = animation.animatedValue as Float
    }
    valueAnimator.start()
    return valueAnimator
}

fun View.showWithAnimate(anim: Int){
    animation = AnimationUtils.loadAnimation(context, anim).apply {
        start()
    }
}
