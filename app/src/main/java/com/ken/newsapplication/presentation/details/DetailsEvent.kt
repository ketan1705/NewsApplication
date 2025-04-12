package com.ken.newsapplication.presentation.details

import com.ken.newsapplication.domain.model.Article

sealed class DetailsEvent {

    data class InsertDeleteArticle(val article: Article) : DetailsEvent()

    object RemoveSideEffect : DetailsEvent()
}