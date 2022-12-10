package com.example.newsapppp.presentation.utils.extensions

import android.text.Editable
import android.view.View
import android.widget.EditText
import com.example.newsapppp.core.SimpleTextChangeListener

//@Suppress("BlockingMethodInNonBlockingContext")
//suspend fun ResponseBody.stringSuspending(): String =
//    withContext(Dispatchers.IO) { string() }
//
//fun ImageView.load(url: String) {
//    Glide.with(this).load(url).into(this)
//}
//
//fun ViewGroup.inflate(@LayoutRes layoutResId: Int): View =
//    LayoutInflater.from(context).inflate(layoutResId, this, false)

fun EditText.listenChanges(action: () -> Unit) {

    addTextChangedListener(object : SimpleTextChangeListener() {
        override fun afterTextChanged(s: Editable?) {
            action.invoke()
        }
    })
}
fun View.invisible(){
    visibility = View.INVISIBLE
}

fun View.visible(){
    visibility = View.VISIBLE
}

fun View.disable(){
    isEnabled = false
}

fun View.enabled(){
    isEnabled = true
}
