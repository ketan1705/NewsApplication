package com.ken.newsapplication.presentation.bookmark.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ken.newsapplication.domain.model.Article
import com.ken.newsapplication.domain.usecases.news.NewsUseCases
import com.ken.newsapplication.presentation.bookmark.BookmarkState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BookmarkViewModel @Inject constructor(
    private val newsUseCases: NewsUseCases,
) : ViewModel() {

    private val _state = mutableStateOf(BookmarkState())
    val state: State<BookmarkState> = _state

    init {
        getArticles()
    }

    private fun getArticles() {
        newsUseCases.selectArticle().onEach {
            _state.value = _state.value.copy(articles = it)
        }.launchIn(viewModelScope)
    }

  /*  private fun deleteArticle(article: Article) {
        viewModelScope.launch {
            newsUseCases.deleteArticle(article)
        }
    }
    private fun deleteArticle(article: Article) {
        viewModelScope.launch {
            newsUseCases.deleteArticle(article)
        }
    }
*/

}