package com.example.newsapppp.presentation.utils.extensions

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.View
import android.view.animation.Animation
import androidx.appcompat.app.AppCompatActivity
import com.example.newsapppp.R
import kotlinx.android.synthetic.main.no_internet_connections.*

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

internal fun AppCompatActivity.internetConnectionDialog(status: String) {
    val dialog = Dialog(this).apply {
        setContentView(R.layout.no_internet_connections)
        internet_status.text = status
        window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        setCanceledOnTouchOutside(false)
        bt_try_again.setOnClickListener {
            dismiss()
        }
        show()
    }
}
