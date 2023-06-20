package com.example.newsapppp.presentation.extensions

import android.app.AlertDialog
import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.PopupMenu
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
import kotlinx.coroutines.launch
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Locale


/**
 *Reformats a date string in the format "yyyy-MM-dd" to "dd MMM yyyy".
 *@param dateInString The date string to reformat.
 *@return The reformatted date string in the format "dd MMM yyyy", or "-" if the input is null or in an invalid format.
 */
fun getReformatDate(dateInString: String?): String {
    return if (dateInString != null) {
        val parser = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val formatter = SimpleDateFormat("dd MMM yyyy", Locale.getDefault())
        try {
            val date = parser.parse(dateInString)
            formatter.format(date!!)
        } catch (e: ParseException) {
            "-"
        }
    } else "-"
}

/**
 * Navigates to the given destination using the Navigation Component.
 * @param where The NavDirections object representing the destination to navigate to.
 */
fun Fragment.navigateDirections(where: NavDirections) = findNavController().navigate(where)

/**
 * Navigates back to the previous screen using the Navigation Component.
 */
fun Fragment.returnToPreviousScreen() = findNavController().navigateUp()

/**
 * Launches a coroutine when the Fragment's view is started.
 * @param block The code block to execute as a coroutine.
 */
inline fun Fragment.launchFragmentScope(crossinline block: suspend CoroutineScope.() -> Unit) {
    viewLifecycleOwner.lifecycleScope.launch { block() }
}

/**
 * Returns the color value associated with the given color resource.
 * @param colorRes The resource ID of the color to retrieve.
 * @return The color value associated with the given resource ID.
 */
fun Fragment.loadColor(@ColorRes colorRes: Int): Int {
    return ContextCompat.getColor(requireContext(), colorRes)
}

/**
 * Shows a Snackbar with the given message and optional action button.
 * @param message The resource ID of the message to display.
 * @param showButton Whether to show an action button or not.
 * @param action The action to perform when the button is clicked.
 */
fun Fragment.showSnackBar(
    message: Int,
    showButton: Boolean = false,
    action: () -> Unit = {}
) {
    val snackBar = Snackbar.make(requireView(), message, Snackbar.LENGTH_LONG)
    val customView =
        layoutInflater.inflate(R.layout.custom_snackbar_layout, requireView() as ViewGroup, false)
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
        (this as Snackbar.SnackbarLayout).addView(customView, 0)
    }
    snackBar.show()
}

/**
 * Shows a dialog with a "Delete" button and a "Cancel" button.
 * @param onSuccess The action to perform when the "Delete" button is clicked.
 * @param onCancel The action to perform when the "Cancel" button is clicked.
 */
fun Fragment.showDeleteDialog(
    onSuccess: () -> Unit,
    onCancel: () -> Unit
) {
    var dialog: AlertDialog? = null
    val builder = AlertDialog.Builder(requireContext())
    val binding = DeleteDialogBinding.inflate(layoutInflater)

    builder.setView(binding.root)
    binding.apply {
        bDelete.setOnClickListener {
            onSuccess.invoke()
            dialog?.dismiss()
        }
        bCansel.setOnClickListener {
            onCancel.invoke()
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

/**
 * Shows an alert dialog with the given title.
 * @param title The resource ID of the title to display.
 */
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

/**
 * Shows a dialog indicating that there is no internet connection.
 * @param status The message to display indicating the status of the internet connection.
 */
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

/**
 * Shows a PopupMenu anchored to the given view.
 * @param view The anchor view for the PopupMenu.
 * @param menuResId The resource ID of the menu to display.
 * @param onMenuItemClickListener The action to perform when a menu item is clicked.
 */
fun Fragment.showPopupMenu(
    view: View,
    menuResId: Int,
    onMenuItemClickListener: (menuItem: MenuItem) -> Unit
) {
    val popupMenu = PopupMenu(requireContext(), view)
    popupMenu.menuInflater.inflate(menuResId, popupMenu.menu)
    popupMenu.setOnMenuItemClickListener { menuItem ->
        onMenuItemClickListener(menuItem)
        true
    }
    popupMenu.show()
}
