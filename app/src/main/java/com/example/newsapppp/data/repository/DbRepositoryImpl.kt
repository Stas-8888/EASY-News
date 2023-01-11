package com.example.newsapppp.data.repository

import com.example.newsapppp.core.DispatchersList
import com.example.newsapppp.core.ManageResources
import com.example.newsapppp.data.db.NewsDao
import com.example.newsapppp.data.mapper.ArticleMapper
import com.example.newsapppp.data.mapper.NewsResponseMapper
import com.example.newsapppp.domain.model.ArticleModel
import com.example.newsapppp.domain.repository.DbRepository
import com.example.newsapppp.domain.repository.SharedPrefRepository
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class DbRepositoryImpl @Inject constructor(
    private val newsDao: NewsDao,
    private val mapper: ArticleMapper,
    private val newsResponseMapper: NewsResponseMapper,
    private val dispatchers: DispatchersList,
    private val sharedPreferences: SharedPrefRepository,
    private val manageResources: ManageResources,
    private var firebaseAuth: FirebaseAuth
) : DbRepository {

    override suspend fun insertArticle(article: ArticleModel) = dispatchers.withContextIO {
        newsDao.insertArticle(mapper.mapFromEntity(article))
    }

    override suspend fun deleteArticle(article: ArticleModel) = dispatchers.withContextIO {
        newsDao.deleteArticle(mapper.mapFromEntity(article))
    }

    override suspend fun deleteAllArticle() = dispatchers.withContextIO {
        newsDao.deleteAllArticle()
    }

    override fun getAllArticles(): Flow<List<ArticleModel>> {
        return newsDao.getAllArticles().map { newsResponseMapper.articleDbToArticleModel(it) }
    }

//    override suspend fun saveDeleteArticleFavorite(article: ArticleModel): NewsState =
//        dispatchers.withContextIO {
//            val isFavorite = false
//            firebaseAuth = FirebaseAuth.getInstance()
//            if (firebaseAuth.currentUser != null) {
//                if (isFavorite == sharedPreferences.getFavorite(mapper.mapFromEntity(article).url)) {
//                    NewsState.ShowAsSaved(
//                        insertArticle(article),
//                        sharedPreferences.saveFavorite(mapper.mapFromEntity(article).url, true),
//                        manageResources.string(R.string.СтатьяДобавлена),
//                        R.drawable.ic_favorite
//                    )
//                } else {
//                    NewsState.ShowUnSaved(
//                        deleteArticle(article),
//                        sharedPreferences.saveFavorite(mapper.mapFromEntity(article).url, false),
//                        manageResources.string(R.string.СтатьяУдалена),
//                        R.drawable.ic_favorite_border
//                    )
//                }
//            } else {
//                NewsState.Error(manageResources.string(R.string.ErrorRegistered))
//            }
//        }
}
