package com.example.newsapppp.core.resources

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

/**
 * This class provides access to Android resources, specifically strings,
 * through the [makeString] function.
 *
 * @property context The [Context] instance used to retrieve the resources.
 */
class ProvideResources @Inject constructor(@ApplicationContext val context: Context) :
    ProvideResourcesContract {

    /**
     * Retrieves a string resource using the given [id].
     *
     * @param id The resource ID of the string to retrieve.
     * @return The retrieved string.
     */
    override fun makeString(id: Int): String = context.getString(id)
}
