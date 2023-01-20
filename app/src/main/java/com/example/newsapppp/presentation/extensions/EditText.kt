package com.example.newsapppp.presentation.extensions

import android.text.Editable
import android.widget.EditText
import com.example.newsapppp.core.TextChangeListener

inline fun EditText.changesListener(crossinline action: () -> Unit) {

    addTextChangedListener(object : TextChangeListener() {
        override fun afterTextChanged(s: Editable?) {
            action.invoke()
        }
    })
}
