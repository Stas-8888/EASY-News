package com.example.newsapppp.common

import com.google.android.material.tabs.TabLayout

/**
 * This abstract class provides a simple implementation of the [TabLayout.OnTabSelectedListener]
 * interface. It provides empty implementations of the [onTabSelected], [onTabUnselected],
 * and [onTabReselected] functions, which can be overridden by subclasses as needed.
 */
abstract class SimpleTabSelectedListener : TabLayout.OnTabSelectedListener {

    /**
     * This function is called when a tab is selected in the TabLayout.
     * The default implementation is empty and does nothing.
     *
     * @param tab The selected tab.
     */
    override fun onTabSelected(tab: TabLayout.Tab) = Unit

    /**
     * This function is called when a tab is unselected in the TabLayout.
     * The default implementation is empty and does nothing.
     *
     * @param tab The unselected tab.
     */
    override fun onTabUnselected(tab: TabLayout.Tab) = Unit

    /**
     * This function is called when a tab is reselected in the TabLayout.
     * The default implementation simply calls [onTabSelected].
     *
     * @param tab The reselected tab.
     */
    override fun onTabReselected(tab: TabLayout.Tab) = onTabSelected(tab)
}
