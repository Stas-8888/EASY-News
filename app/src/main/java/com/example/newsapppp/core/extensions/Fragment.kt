package com.example.newsapppp.core.extensions

import android.app.AlertDialog
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import com.example.newsapppp.R
import com.example.newsapppp.databinding.DeleteDialog3Binding
import com.example.newsapppp.databinding.SnackbarBinding
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar
import com.tapadoo.alerter.Alerter
import kotlinx.coroutines.CoroutineScope

fun Fragment.toast(message: String) = Toast.makeText(activity, message, Toast.LENGTH_SHORT).show()

fun Fragment.navigateTo(where: Int) = findNavController().navigate(where)

fun Fragment.navigateDirections(where: NavDirections) = findNavController().navigate(where)

fun Fragment.launchWhenStarted(block: suspend CoroutineScope.() -> Unit) {
    lifecycleScope.launchWhenStarted(block)
}

fun Fragment.loadColor(@ColorRes colorRes: Int): Int {
    return ContextCompat.getColor(requireContext(), colorRes)
}

fun Fragment.showSnackBar(view: View, title: String, okFun: () -> Unit = {}) {
    val snackView = View.inflate(view?.context, R.layout.snackbar, null)
    val binding = SnackbarBinding.bind(snackView)
    val snackbar = Snackbar.make(view, "", Snackbar.LENGTH_LONG)
    (snackbar.view as ViewGroup).removeAllViews()
    (snackbar.view as ViewGroup).addView(binding.root)
//    snackbar.view.setPadding(0, 0, 0, 0)
//    snackbar.view.elevation = 0f
    snackbar.setBackgroundTint(
        ContextCompat.getColor(
            view.context,
            android.R.color.transparent
        )
    )
    binding.snackbarTitle.text = title
    binding.snackbarAction.setOnClickListener {
        okFun()
        snackbar.dismiss()
    }
    snackbar.show()
}

fun Fragment.showSnackbar(view: View, message: String, isError: Boolean = false, action: () -> Unit = {}) {
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

fun Fragment.snackBar(view: View, title: String){
    Snackbar.make(view,title, Snackbar.LENGTH_LONG)
        .setAnimationMode(BaseTransientBottomBar.ANIMATION_MODE_FADE)
        .setBackgroundTint(Color.parseColor("#E30105"))
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

fun Fragment.showAlertUpDialog(title:String) {
    activity?.let {
        Alerter.create(it)
            .setTitle(title)
            .setIcon(R.drawable.ic_breaking_news)
            .setBackgroundColorRes(R.color.colorRed)
            .show()
    }
}
