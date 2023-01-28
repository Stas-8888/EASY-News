package com.example.newsapppp.core

import com.google.android.material.tabs.TabLayout

abstract class SimpleTabSelectedListener : TabLayout.OnTabSelectedListener {
    override fun onTabSelected(tab: TabLayout.Tab) = Unit
    override fun onTabUnselected(tab: TabLayout.Tab) = Unit
    override fun onTabReselected(tab: TabLayout.Tab) = onTabSelected(tab)
}
