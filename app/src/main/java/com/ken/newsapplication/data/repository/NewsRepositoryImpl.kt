package com.ken.newsapplication.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.ken.newsapplication.data.local.NewsDao
import com.ken.newsapplication.data.remote.NewsAPI
import com.ken.newsapplication.data.remote.SearchNewsPagingSource
import com.ken.newsapplication.data.remote.dto.NewsPagingSource
import com.ken.newsapplication.domain.model.Article
import com.ken.newsapplication.domain.repository.NewsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.onEach

class NewsRepositoryImpl(
    private val newsApi: NewsAPI,
    private val newsDao: NewsDao,
) : NewsRepository {

    override fun getNews(
        sources: List<String>,
    ): Flow<PagingData<Article>> {

        return Pager(
            config = PagingConfig(pageSize = 10),
            pagingSourceFactory = {
                NewsPagingSource(
                    newsApi = newsApi,
                    sources = sources.joinToString(separator = ",")
                )
            }
        ).flow

    }


    override fun searchNews(
        searchQuery: String,
        sources: List<String>,
    ): Flow<PagingData<Article>> {

        return Pager(
            config = PagingConfig(pageSize = 10),
            pagingSourceFactory = {
                SearchNewsPagingSource(
                    searchQuery = searchQuery,
                    newsApi = newsApi,
                    sources = sources.joinToString(separator = ",")
                )
            }
        ).flow

    }

    override suspend fun insertArticle(article: Article) {
        newsDao.insertArticle(article)
    }

    override suspend fun deleteArticle(article: Article) {
        newsDao.deleteArticle(article)
    }

    override fun selectArticles(): Flow<List<Article>> {
        return newsDao.getArticles().onEach { it.reversed() }
    }

    override suspend fun selectSingleArticle(url: String): Article? {
        return newsDao.getArticle(url)
    }


}