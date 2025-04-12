package com.ken.newsapplication.domain.usecases.news

import com.ken.newsapplication.domain.usecases.news.articlesoperation.DeleteArticle
import com.ken.newsapplication.domain.usecases.news.articlesoperation.InsertArticle
import com.ken.newsapplication.domain.usecases.news.articlesoperation.SelectArticle
import com.ken.newsapplication.domain.usecases.news.articlesoperation.SelectSingleArticle

data class NewsUseCases(
    val getNews: GetNews,
    val searchNews: SearchNews,
    val insertArticle: InsertArticle,
    val deleteArticle: DeleteArticle,
    val selectArticle: SelectArticle,
    val selectedSingleArticle: SelectSingleArticle,
)