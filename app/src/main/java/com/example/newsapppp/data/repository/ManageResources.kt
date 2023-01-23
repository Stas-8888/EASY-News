package com.example.newsapppp.data.repository

import android.content.Context
import com.example.newsapppp.core.ManageResourcesContract
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class ManageResources @Inject constructor(@ApplicationContext val context: Context) :
    ManageResourcesContract {
    override fun string(id: Int): String = context.getString(id)
}
