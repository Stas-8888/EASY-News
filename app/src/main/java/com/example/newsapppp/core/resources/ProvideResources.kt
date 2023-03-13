package com.example.newsapppp.core.resources

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class ProvideResources @Inject constructor(@ApplicationContext val context: Context) :
    ProvideResourcesContract {
    override fun makeString(id: Int): String = context.getString(id)
}
