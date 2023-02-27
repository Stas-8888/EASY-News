package com.example.newsapppp.presentation.screens.activity

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavDestination
import com.example.newsapppp.R
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

class MainActivityViewModel : ViewModel() {

    private val _state = MutableStateFlow<MainActivityState>(MainActivityState.Success)
    val state = _state.asSharedFlow()

    fun setupBottomNavigation(destination: NavDestination) = viewModelScope.launch {
        when (destination.id) {
            R.id.favoriteFragment,
            R.id.mainFragment,
            R.id.searchFragment -> _state.emit(MainActivityState.Success)
            else -> _state.emit(MainActivityState.Failure)
        }
    }
}