package com.ken.newsapplication.data.remote.dto

import com.ken.newsapplication.domain.model.Article

data class NewsResponse(
    val articles: List<Article>,
    val status: String,
    val totalResults: Int
)