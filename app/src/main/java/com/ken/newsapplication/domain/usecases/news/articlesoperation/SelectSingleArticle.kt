package com.ken.newsapplication.domain.usecases.news.articlesoperation

import com.ken.newsapplication.domain.model.Article
import com.ken.newsapplication.domain.repository.NewsRepository

class SelectSingleArticle(
    private val newsRepository: NewsRepository,

    ) {

    suspend operator fun invoke(url: String): Article? {
        return newsRepository.selectSingleArticle(url = url)
    }
}