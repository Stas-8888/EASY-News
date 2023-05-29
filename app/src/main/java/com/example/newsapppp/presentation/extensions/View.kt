package com.example.newsapppp.presentation.extensions

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Context
import android.view.View
import android.view.animation.AnimationUtils
import android.view.animation.TranslateAnimation
import android.view.inputmethod.InputMethodManager
import androidx.core.animation.doOnEnd
import com.example.newsapppp.R
import com.example.newsapppp.core.Constants.ZERO

/**
 * Sets the visibility of the view to GONE.
 */
fun View.makeGone() {
    visibility = View.GONE
}

/**
 * Sets the visibility of the view to VISIBLE.
 */
fun View.makeVisible() {
    visibility = View.VISIBLE
}

/**
 * Sets the visibility of this view to either View.VISIBLE or View.INVISIBLE depending on the value of the provided boolean.
 * @param data a boolean indicating whether to show or hide the view
 */
fun View.visibility(data: Boolean) {
    visibility = if (data) View.VISIBLE else View.INVISIBLE
}

/**
 * Animates this view to slide down.
 */
fun View.slideDownAnimation() {
    val height = this.height
    ObjectAnimator.ofFloat(this, View.TRANSLATION_Y, 0f, height.toFloat()).apply {
        duration = 1000
        start()
    }
}

/**
 * Animates this view to slide up.
 */
fun View.slideUpAnimation() {
    val height = this.height
    ObjectAnimator.ofFloat(this, View.TRANSLATION_Y, height.toFloat(), 0f).apply {
        duration = 1000
        start()
    }
}

/**
 * Animates this view to shake back and forth horizontally.
 */
fun View.shakeAnimation() =
    ObjectAnimator.ofFloat(this, "translationX", 0F, -15F, 15F, -10F, 10F, -5F, 5F, 0F)
        .setDuration(500)
        .start()

/**
 * Animates this view to fade in by changing its alpha value from 0f to 1f.
 * @return an ObjectAnimator that can be used to start or cancel the animation
 */
fun View.fadeInAnimation(): ObjectAnimator =
    ObjectAnimator.ofFloat(this, View.ALPHA, 0f, 1f).apply {
        duration = 250
        start()
    }

/**
 * Animates this view to fade out by changing its alpha value from 1f to 0f.
 * @return an ObjectAnimator that can be used to start or cancel the animation
 */
fun View.fadeOutAnimation(): ObjectAnimator =
    ObjectAnimator.ofFloat(this, View.ALPHA, 1f, 0f).apply {
        duration = 250
        start()
    }

/**
 * Animates this view to bump in size by scaling it up and then back down.
 * @param action a lambda expression to be executed when the animation ends
 */
fun View.bumpAnimation(action: () -> Unit = {}) {
    val x = ObjectAnimator.ofFloat(this, "scaleX", 1F, 1.1F, 1F)
    val y = ObjectAnimator.ofFloat(this, "scaleY", 1F, 1.1F, 1F)

    AnimatorSet().apply {
        playTogether(x, y)
        duration = 250
        doOnEnd { action() }
        start()
    }
}

/**
 * Starts an animation on this view using the R.anim.fab_explode animation resource.
 */
fun View.clickAnimation() {
    startAnimation(AnimationUtils.loadAnimation(context, R.anim.fab_explode))
}

/**
 * Requests focus for this view and shows the keyboard.
 */
fun View.showKeyboard() {
    val inputMethodManager =
        context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    requestFocus()
    inputMethodManager.showSoftInput(this, ZERO)
}

/**
 * Hides the keyboard.
 */
fun View.hideKeyboard() {
    (context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager).apply {
        hideSoftInputFromWindow(windowToken, ZERO)
    }
}

/**
 * Shows this view with the provided animation.
 * @param anim the animation resource to be used to animate this view
 */
fun View.showWithAnimate(anim: Int) {
    animation = AnimationUtils.loadAnimation(context, anim).apply {
        start()
    }
}

fun View.translateAnimation(fromX: Float, toX: Float, fromY: Float, toY: Float, duration: Long) {
    startAnimation(TranslateAnimation(fromX, toX, fromY, toY).apply {
        this.duration = duration
    })
}
