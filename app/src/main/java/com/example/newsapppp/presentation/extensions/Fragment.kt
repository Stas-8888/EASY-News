package com.example.newsapppp.presentation.extensions

import android.app.AlertDialog
import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import com.example.newsapppp.R
import com.example.newsapppp.databinding.DeleteDialogBinding
import com.google.android.material.snackbar.Snackbar
import com.tapadoo.alerter.Alerter
import kotlinx.coroutines.CoroutineScope

fun Fragment.navigateDirections(where: NavDirections) = findNavController().navigate(where)

fun Fragment.returnToPreviousScreen() = findNavController().navigateUp()

fun Fragment.launchWhenStarted(block: suspend CoroutineScope.() -> Unit) {
    viewLifecycleOwner.lifecycleScope.launchWhenStarted(block)
}

fun Fragment.loadColor(@ColorRes colorRes: Int): Int {
    return ContextCompat.getColor(requireContext(), colorRes)
}

fun Fragment.showSnackBar(
    message: Int,
    showButton: Boolean = false,
    action: () -> Unit = {}
) {
    val snackBar = Snackbar.make(requireView(), message, Snackbar.LENGTH_LONG)
    val customView = layoutInflater.inflate(R.layout.custom_snackbar_layout, null)
    val button = customView.findViewById<Button>(R.id.snack_bar_action)
    val title = customView.findViewById<TextView>(R.id.snack_bar_text)
    title.text = context?.getString(message)

    if (showButton) {
        button.visibility = View.VISIBLE
        button.setOnClickListener {
            action()
            snackBar.dismiss()
        }
    } else {
        button.visibility = View.GONE
    }
    snackBar.view.apply {
        setBackgroundColor(Color.TRANSPARENT)
//        setPadding(0, 0, 0, 0)
        (this as Snackbar.SnackbarLayout).addView(customView, 0)
    }
    snackBar.show()
}

fun Fragment.showDeleteDialog(onSuccess: () -> Unit, noteSuccess: () -> Unit) {
    var dialog: AlertDialog? = null
    val builder = AlertDialog.Builder(context)
    val binding = DeleteDialogBinding.inflate(LayoutInflater.from(context))

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
        window?.attributes?.windowAnimations = R.style.DialogAnimation
        show()
    }
}

fun Fragment.showAlertUpDialog(title: Int) {
    activity?.let {
        Alerter.create(it)
            .setTitle(title)
            .setIcon(R.drawable.ic_breaking_news)
            .enableSwipeToDismiss()
            .setBackgroundResource(R.color.color_red_background)
            .show()
    }
}

fun Fragment.showInternetConnectionDialog(status: String) {
    Dialog(requireContext()).apply {
        setContentView(R.layout.no_internet_connections)
        val internetStatus = findViewById<TextView>(R.id.internet_status)
        val btTry = findViewById<Button>(R.id.bt_try_again)
        internetStatus.text = status
        window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        setCanceledOnTouchOutside(false)
        btTry.setOnClickListener {
            dismiss()
        }
        show()
    }
}
