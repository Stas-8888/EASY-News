package com.example.newsapppp.core.resources

import androidx.annotation.StringRes

interface ProvideResourcesRepository {

    fun makeString(@StringRes id: Int): String
}
