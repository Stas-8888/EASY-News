package com.example.newsapppp.presentation.fragments.main

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.util.Log
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsapppp.domain.usecase.GetCountryFlagUseCase
import com.example.newsapppp.domain.usecase.GetNewsUseCase
import com.example.newsapppp.domain.usecase.GetSwitchPositionUseCase
import com.example.newsapppp.presentation.App
import com.example.newsapppp.presentation.mapper.ArticleMapperToModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainFragmentViewModel @Inject constructor(
    app: Application,
    private val getNewsUseCase: GetNewsUseCase,
    private val articleMapperToModel: ArticleMapperToModel,
    private val getCountryFlagUseCase: GetCountryFlagUseCase,
    private val getSwitchPositionUseCase: GetSwitchPositionUseCase
) : AndroidViewModel(app) {

    private val _state = MutableStateFlow<MainState>(MainState.ShowLoading)
    val state: StateFlow<MainState> = _state

    fun getNewsRetrofit(countryCode: String, category: String) = viewModelScope.launch {
        if (checkInternetConnections()) {
            getNews(countryCode, category)
        } else {
            _state.emit(MainState.HideLoading)
        }
    }

    private suspend fun getNews(countryCode: String, category: String) {
        val data = getNewsUseCase.getNews(
            countryCode = countryCode, category = category
        ).articlesModel.filter { it.urlToImage != null && it.url != null }
        _state.emit(MainState.ShowArticles(articleMapperToModel.articleToModelArticle(data)))
    }

    fun getCountryFlag(): String {
        return getCountryFlagUseCase.getCountryFlag()
    }

    private fun getSwitchPosition(): Boolean {
        return getSwitchPositionUseCase.getSwitchPosition()
    }

    private fun checkInternetConnections(): Boolean {
        val connectivityManager = getApplication<App>()
            .getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val capabilities =
                connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
            if (capabilities != null) {
                if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)) {
                    Log.d("Internet", "NetworkCapabilities.TRANSPORT_CELLULAR")
                    return true
                } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {
                    Log.d("Internet", "NetworkCapabilities.TRANSPORT_WIFI")
                    return true
                }
            } else {
                connectivityManager.activeNetworkInfo?.run {
                    return when (type) {
                        ConnectivityManager.TYPE_WIFI -> true
                        ConnectivityManager.TYPE_MOBILE -> true
                        ConnectivityManager.TYPE_ETHERNET -> true
                        else -> false
                    }
                }
            }
        }
        return false
    }

    fun setupDayNight() {
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
