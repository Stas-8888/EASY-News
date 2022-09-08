package com.example.newsapppp.presentation

import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsapppp.domain.usecase.GetSwitchPositionUseCase
import com.example.newsapppp.domain.usecase.SearchNewsUseCase
import com.example.newsapppp.presentation.mapper.ArticleMapperToModel
import com.example.newsapppp.presentation.model.Article
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(
    private val getSwitchPositionUseCase: GetSwitchPositionUseCase
) : ViewModel() {

    private fun getSwitchPosition(): Boolean {
        return getSwitchPositionUseCase.getSwitchPosition()
    }

    fun setupDayNightMode() = viewModelScope.launch{
        val nightMode = getSwitchPosition()
        if (nightMode) {
            AppCompatDelegate
                .setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
        } else {
            AppCompatDelegate
                .setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        }
    }
}
