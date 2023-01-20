package com.example.newsapppp.presentation.utils.extensions

import android.text.Editable
import android.widget.EditText
import com.example.newsapppp.presentation.utils.TextChangeListener

fun EditText.changesListener(action: () -> Unit) {

    addTextChangedListener(object : TextChangeListener() {
        override fun afterTextChanged(s: Editable?) {
            action.invoke()
        }
    })
}
