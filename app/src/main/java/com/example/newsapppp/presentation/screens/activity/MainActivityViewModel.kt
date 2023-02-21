package com.example.newsapppp.presentation.screens.activity

import android.animation.ObjectAnimator
import android.view.View
import androidx.lifecycle.ViewModel
import androidx.navigation.NavDestination
import com.example.newsapppp.R
import com.example.newsapppp.presentation.extensions.invisible
import com.example.newsapppp.presentation.extensions.visible
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow

class MainActivityViewModel: ViewModel() {

    val _state = MutableStateFlow<MainActivityState>(MainActivityState.Success)
    val state = _state.asSharedFlow()

     private fun slideDown(view: View) {
        val height = view.height
        ObjectAnimator.ofFloat(view, View.TRANSLATION_Y, 0f, height.toFloat()).apply {
            duration = 400
            start()
        }
    }

     private fun slideUp(view: View) {
        val height = view.height
        ObjectAnimator.ofFloat(view, View.TRANSLATION_Y, height.toFloat(), 0f).apply {
            duration = 450
            start()
        }
    }

    fun setupBottomNavigation(destination: NavDestination, bottomNavigationView: View){
        when (destination.id) {
            R.id.saveFragment,
            R.id.mainFragment,
            R.id.searchFragment -> {
                slideUp(bottomNavigationView)
                bottomNavigationView.visible()
            }
            else -> {
                slideDown(bottomNavigationView)
                bottomNavigationView.invisible()
            }
        }
    }

}