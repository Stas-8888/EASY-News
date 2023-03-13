package com.example.newsapppp.core.resources

import androidx.annotation.StringRes

interface ProvideResourcesContract {

    fun makeString(@StringRes id: Int): String
}
