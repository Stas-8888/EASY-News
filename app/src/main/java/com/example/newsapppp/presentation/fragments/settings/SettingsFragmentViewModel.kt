package com.example.newsapppp.presentation.fragments.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsapppp.domain.usecase.GetFavoriteUseCase
import com.example.newsapppp.domain.usecase.SaveFavoriteUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsFragmentViewModel @Inject constructor(
    private val saveFavoriteUseCase: SaveFavoriteUseCase,
    private val getFavoriteUseCase: GetFavoriteUseCase
) : ViewModel() {

    fun saveFavorite(value: Boolean)= viewModelScope.launch {
        saveFavoriteUseCase.saveFavorite(value)
    }

    fun getFavorite(): Boolean  {
        return getFavoriteUseCase.getFavorite()
    }

}
