package com.ken.newsapplication.presentation.bookmark

import com.ken.newsapplication.domain.model.Article

data class BookmarkState(
    val articles: List<Article> = emptyList(),
)