package com.example.newsapppp.common.resources

import androidx.annotation.StringRes

interface ProvideResourcesRepository {

    fun makeString(@StringRes id: Int): String
}
