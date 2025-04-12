package com.ken.newsapplication.presentation.details.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ken.newsapplication.domain.model.Article
import com.ken.newsapplication.domain.usecases.news.NewsUseCases
import com.ken.newsapplication.presentation.details.DetailsEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val newsUseCases: NewsUseCases,
) : ViewModel() {
    var sideEffect by mutableStateOf<String?>(null)
        private set

    fun onEvent(event: DetailsEvent) {
        when (event) {
            is DetailsEvent.InsertDeleteArticle -> {
                viewModelScope.launch {
                    val article = newsUseCases
                        .selectedSingleArticle(event.article.url)
                    if (article == null) {
                        insertArticle(event.article)
                    } else {
                        deleteArticle(event.article)
                    }
                }
            }

            is DetailsEvent.RemoveSideEffect -> {
                sideEffect = null
            }

        }
    }

    private suspend fun deleteArticle(article: Article) {
        newsUseCases.deleteArticle(article = article)
        sideEffect = "Article Deleted"
    }

    private suspend fun insertArticle(article: Article) {
        newsUseCases.insertArticle(article = article)
        sideEffect = "Article Saved"

    }
}