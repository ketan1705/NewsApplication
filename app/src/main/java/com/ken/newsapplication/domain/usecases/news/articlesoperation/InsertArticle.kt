package com.ken.newsapplication.domain.usecases.news.articlesoperation

import com.ken.newsapplication.domain.model.Article
import com.ken.newsapplication.domain.repository.NewsRepository

class InsertArticle(
    private val newsRepository: NewsRepository,
) {

    suspend operator fun invoke(article: Article) {
        newsRepository.insertArticle(article)
    }
}