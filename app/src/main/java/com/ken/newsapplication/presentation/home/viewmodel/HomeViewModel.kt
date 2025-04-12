package com.ken.newsapplication.presentation.home.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.ken.newsapplication.domain.usecases.news.NewsUseCases
import com.ken.newsapplication.util.Constants.SOURCES
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val newsUseCases: NewsUseCases,
) : ViewModel() {
    val news = newsUseCases.getNews(
        source = SOURCES
    ).cachedIn(viewModelScope)
}