package com.example.newsapppp.presentation.extensions

import android.view.View
import androidx.annotation.StringRes
import com.example.newsapppp.presentation.customview.customsnackbar.CustomSnackBar
import com.google.android.material.snackbar.Snackbar

/**
 * Display the Snackbar with the [Snackbar.LENGTH_SHORT] duration.
 *
 * @param message the message text resource.
 */
inline fun View.snackbar(@StringRes message: Int) = CustomSnackBar
    .make(this, message, Snackbar.LENGTH_SHORT)
    .apply { show() }

/**
 * Display the Snackbar with the [Snackbar.LENGTH_SHORT] duration.
 *
 * @param message the message text resource.
 */
inline fun View.showActionSnackBar(
    message: Int,
    @StringRes actionText: Int,
    noinline action: (View) -> Unit = {}
) = CustomSnackBar
    .make(this, message, Snackbar.LENGTH_SHORT)
    .setAction(actionText, action)
    .apply { show() }

/**
 * Display Snackbar with the [Snackbar.LENGTH_LONG] duration.
 *
 * @param message the message text resource.
 */
inline fun View.longSnackbar(
    @StringRes message: Int,
    @StringRes actionText: Int,
    noinline action: (View) -> Unit
) =
    CustomSnackBar
        .make(this, message, Snackbar.LENGTH_LONG)
        .setAction(actionText, action)
        .apply { show() }

/**
 * Display Snackbar with the [Snackbar.LENGTH_INDEFINITE] duration.
 *
 * @param message the message text resource.
 */
inline fun View.indefiniteSnackbar(
    @StringRes message: Int,
    @StringRes actionText: Int,
    noinline action: (View) -> Unit
) =
    CustomSnackBar
        .make(this, message, Snackbar.LENGTH_INDEFINITE)
        .setAction(actionText, action)
        .apply { show() }


/**
 * Display Snackbar with the [Snackbar.LENGTH_LONG] duration.
 *
 * @param message the message text.
 */
inline fun View.longSnackbar(
    message: CharSequence,
    actionText: CharSequence,
    noinline action: (View) -> Unit
) = CustomSnackBar
    .make(this, message, Snackbar.LENGTH_LONG)
    .setAction(actionText, action)
    .apply { show() }

/**
 * Display Snackbar with the [Snackbar.LENGTH_INDEFINITE] duration.
 *
 * @param message the message text.
 */
inline fun View.indefiniteSnackbar(
    message: CharSequence,
    actionText: CharSequence,
    noinline action: (View) -> Unit
) = CustomSnackBar
    .make(this, message, Snackbar.LENGTH_INDEFINITE)
    .setAction(actionText, action)
    .apply { show() }
