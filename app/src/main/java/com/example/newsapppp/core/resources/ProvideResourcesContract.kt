package com.example.newsapppp.core.resources

import androidx.annotation.StringRes

interface ProvideResourcesContract {

    fun string(@StringRes id: Int): String
}
