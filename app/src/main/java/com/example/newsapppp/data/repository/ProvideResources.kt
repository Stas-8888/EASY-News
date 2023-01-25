package com.example.newsapppp.data.repository

import android.content.Context
import com.example.newsapppp.core.ProvideResourcesContract
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class ProvideResources @Inject constructor(@ApplicationContext val context: Context) :
    ProvideResourcesContract {
    override fun string(id: Int): String = context.getString(id)
}
