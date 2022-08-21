package com.example.newsapppp.presentation.fragments.news

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsapppp.domain.usecase.DeleteArticleUseCase
import com.example.newsapppp.domain.usecase.InsertArticleUseCase
import com.example.newsapppp.presentation.mapper.ArticleMapperToModel
import com.example.newsapppp.presentation.model.Article
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewsFragmentViewModel @Inject constructor(
    application: Application,
    private val insertArticleUseCase: InsertArticleUseCase,
    private val deleteArticleUseCase: DeleteArticleUseCase,
    private val articleMapperToModel: ArticleMapperToModel
) : AndroidViewModel(application) {

    fun insert(article: Article) = viewModelScope.launch {
        insertArticleUseCase.insert(articleMapperToModel.convertToModel(article))
    }

    fun delete(article: Article) = viewModelScope.launch {
        deleteArticleUseCase.delete(articleMapperToModel.convertToModel(article))
    }
}
