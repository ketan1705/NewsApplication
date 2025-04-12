package com.ken.newsapplication.domain.usecases.news.articlesoperation

import com.ken.newsapplication.data.local.NewsDao
import com.ken.newsapplication.domain.model.Article
import com.ken.newsapplication.domain.repository.NewsRepository

class DeleteArticle(
    private val newsRepository: NewsRepository,
) {

    suspend operator fun invoke(article: Article) {
        newsRepository.deleteArticle(  article)
    }
}