package com.example.newsapppp.presentation.fragments.search

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsapppp.domain.usecase.SearchNewsUseCase
import com.example.newsapppp.presentation.mapper.ArticleMapperToModel
import com.example.newsapppp.presentation.model.Article
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchFragmentViewModel @Inject constructor(
    private val searchNewsUseCase: SearchNewsUseCase,
    private val articleMapperToModel: ArticleMapperToModel
) : ViewModel() {

    val searchNews: MutableLiveData<List<Article>> = MutableLiveData()

    fun getSearchRetrofit(searchQuery: String) = viewModelScope.launch {
        val data = searchNewsUseCase.searchNews(searchQuery).articlesModel
        searchNews.value = articleMapperToModel.articleToModelArticle(data)
    }
}
