package com.ken.newsapplication.domain.usecases.news

import androidx.paging.PagingData
import com.ken.newsapplication.domain.model.Article
import com.ken.newsapplication.domain.repository.NewsRepository
import kotlinx.coroutines.flow.Flow

class GetNews(
    private val newsRepository: NewsRepository,
) {

    operator fun invoke(
        source: List<String>,
    ): Flow<PagingData<Article>> {
        return newsRepository.getNews(sources = source)
    }

}