package com.ken.newsapplication.domain.usecases.news.articlesoperation

import com.ken.newsapplication.data.local.NewsDao
import com.ken.newsapplication.domain.model.Article
import com.ken.newsapplication.domain.repository.NewsRepository
import kotlinx.coroutines.flow.Flow

class SelectArticle(
    private val newsRepository: NewsRepository,

    ) {

    operator fun invoke(): Flow<List<Article>> {
        return newsRepository.selectArticles()
    }
}