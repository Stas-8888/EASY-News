package com.example.newsapppp.core.extensions

import android.text.Editable
import android.widget.EditText
import com.example.newsapppp.core.TextChangeListener

fun EditText.listenChanges(action: () -> Unit) {

    addTextChangedListener(object : TextChangeListener() {
        override fun afterTextChanged(s: Editable?) {
            action.invoke()
        }
    })
}
