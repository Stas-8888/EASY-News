package com.example.newsapppp.presentation.extensions

import android.app.Activity
import android.app.AlertDialog
import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import com.example.newsapppp.R
import com.example.newsapppp.databinding.DeleteDialog3Binding
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar
import com.tapadoo.alerter.Alerter
import kotlinx.coroutines.CoroutineScope

internal fun Fragment.showNoActionOkDialog(title: Int, content: CharSequence?) {
    MaterialAlertDialogBuilder(requireContext())
        .setTitle(title)
        .setMessage(content)
        .setPositiveButton(R.string.Cansel) { dialog, _ ->
            dialog.dismiss()
        }.show()
}

//private fun openDeleteAlertDialog(title: Int,) {
//    MaterialAlertDialogBuilder(requireContext())
//        .setTitle(title)
//        .setMessage(R.string.irreversible)
//        .setIcon(R.drawable.ic_delete)
//        .setPositiveButton(R.string.Delete) { _, _ ->
//            settingsViewModel.deleteAllData()
//        }
//        .setNegativeButton(android.R.string.cancel) { dialog, _ ->
//            dialog.dismiss()
//        }
//        .show()
//}

fun Fragment.navigateTo(where: Int) = findNavController().navigate(where)

fun Fragment.navigateDirections(where: NavDirections) = findNavController().navigate(where)

fun Fragment.launchWhenStarted(block: suspend CoroutineScope.() -> Unit) {
    lifecycleScope.launchWhenStarted(block)
}

fun Fragment.loadColor(@ColorRes colorRes: Int): Int {
    return ContextCompat.getColor(requireContext(), colorRes)
}

fun Fragment.showSnackbar(
    view: View,
    message: String,
    isError: Boolean = false,
    action: () -> Unit = {}
) {
    val sb = Snackbar.make(view, message, Snackbar.LENGTH_LONG)
    if (isError)
        sb.setBackgroundTint(loadColor(R.color.colorRed))
            .setTextColor(loadColor(R.color.white))
            .setAction("Ok") {
                action()
            }.show()
    else
        sb.setBackgroundTint(loadColor(R.color.colorSecondary))
            .setTextColor(loadColor(R.color.black))
            .setAction("Cancel") {
                action
            }.show()
}

fun Fragment.snackBar(view: View, title: Int) {
    Snackbar.make(view, title, 1800)
        .setAnimationMode(BaseTransientBottomBar.ANIMATION_MODE_FADE)
        .setBackgroundTint(loadColor(R.color.colorRedBackground))
        .setTextColor(Color.WHITE)
        .show()
}

fun Fragment.showSnackBarString(view: View, title: String?) {
    Snackbar.make(view, title.toString(), 1500)
        .setAnimationMode(BaseTransientBottomBar.ANIMATION_MODE_FADE)
        .setBackgroundTint(loadColor(R.color.colorRedBackground))
        .setTextColor(Color.WHITE)
        .show()
}

fun Fragment.showDeleteDialog(onSuccess: () -> Unit, noteSuccess: () -> Unit) {
    var dialog: AlertDialog? = null
    val builder = AlertDialog.Builder(context)
    val binding = DeleteDialog3Binding.inflate(LayoutInflater.from(context))

    builder.setView(binding.root)
    binding.apply {
        bDelete.setOnClickListener {
            onSuccess.invoke()
            dialog?.dismiss()
        }
        bCansel.setOnClickListener {
            noteSuccess.invoke()
            dialog?.dismiss()
        }
    }

    dialog = builder.create().apply {
        setCanceledOnTouchOutside(false)
        window?.setBackgroundDrawable(null)
        window?.setBackgroundDrawableResource(android.R.color.transparent)
        show()
    }
}

fun Fragment.showAlertUpDialog(title: Int) {
    activity?.let {
        Alerter.create(it)
            .setTitle(title)
            .setIcon(R.drawable.ic_breaking_news)
            .enableSwipeToDismiss()
            .setBackgroundResource(R.color.colorRedBackground)
            .show()
    }
}

internal fun Fragment.internetConnectionDialog(status: String) {
    Dialog(requireContext()).apply {
        setContentView(R.layout.no_internet_connections)
//        internet_status.text = status
        window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        setCanceledOnTouchOutside(false)
//        bt_try_again.setOnClickListener {
//            dismiss()
//        }
        show()
    }
}

fun hideKeyboard(activity: Activity, view: View) {
    val inputMethodManager =
        activity.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
}

//fun Fragment.validation(email: String, password: String): Boolean {
//    var isValid = true
//
//    if (email.isNullOrEmpty()) {
//        isValid = false
//        context?.getString(R.string.empty_email)
//    } else {
//        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
//            isValid = false
//            context?.getString(R.string.InvalidEmail)
//        }
//    }
//    if (password.isNullOrEmpty()) {
//        isValid = false
//        context?.getString(R.string.empty_password)
//    } else {
//        if (password.length < 6) {
//            isValid = false
//            context?.getString(R.string.MinimumCharacter)
//        }
//    }
//    return isValid
//}
