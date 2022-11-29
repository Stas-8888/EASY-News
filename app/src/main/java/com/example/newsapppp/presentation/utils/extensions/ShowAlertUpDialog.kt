package com.example.newsapppp.presentation.utils.extensions

import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsapppp.R
import com.tapadoo.alerter.Alerter
import kotlinx.coroutines.launch

fun Fragment.showAlertUpDialog(title:String) {
    activity?.let {
        Alerter.create(it)
            .setTitle(title)
            .setIcon(R.drawable.ic_breaking_news)
            .setBackgroundColorRes(R.color.colorRed)
            .show()
    }
}
