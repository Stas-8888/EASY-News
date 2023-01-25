package com.example.newsapppp.core

import androidx.annotation.StringRes

interface ProvideResourcesContract {

    fun string(@StringRes id: Int): String
}
