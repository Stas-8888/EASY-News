package com.example.newsapppp.presentation.extensions

import androidx.fragment.app.Fragment
import com.example.newsapppp.R
import com.tapadoo.alerter.Alerter

fun Fragment.showAlertUpDialog(title:String) {
    activity?.let {
        Alerter.create(it)
            .setTitle(title)
            .setIcon(R.drawable.ic_breaking_news)
            .setBackgroundColorRes(R.color.colorRed)
            .show()
    }
}

