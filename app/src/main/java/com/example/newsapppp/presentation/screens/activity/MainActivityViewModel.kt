package com.example.newsapppp.presentation.screens.activity

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavDestination
import com.example.newsapppp.R
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

/**
 * This class represents the ViewModel for the MainActivity.
 */
class MainActivityViewModel : ViewModel() {

    private val _state = MutableStateFlow<MainActivityState>(MainActivityState.BottomNavigationState(false))
    val state = _state.asSharedFlow()

    /**
     * Sets up the bottom navigation bar based on the current destination.
     * @param destination The NavDestination of the current fragment.
     */
    fun setupBottomNavigation(destination: NavDestination) = viewModelScope.launch {
        when (destination.id) {
            R.id.favoriteFragment,
            R.id.mainFragment,
            R.id.searchFragment -> _state.emit(MainActivityState.BottomNavigationState(true))
            else -> _state.emit(MainActivityState.BottomNavigationState(false))
        }
    }
}
