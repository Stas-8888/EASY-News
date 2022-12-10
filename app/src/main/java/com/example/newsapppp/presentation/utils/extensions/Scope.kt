package com.example.newsapppp.presentation.utils.extensions

import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlin.coroutines.CoroutineContext

fun Fragment.launchWhenStarted(block: suspend CoroutineScope.() -> Unit) {
    lifecycleScope.launchWhenStarted(block)
}

