package com.example.newsapppp.core

import androidx.annotation.StringRes

interface ManageResourcesContract {

    fun string(@StringRes id: Int): String
}
