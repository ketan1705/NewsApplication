package com.ken.newsapplication.util

import com.ken.newsapplication.BuildConfig

object Constants {
    const val USER_SETTINGS = "user_settings"
    const val APP_ENTRY = "app_entry"
    const val TAG = "***NEWS_APP***"
    const val DATABASE_NAME = "news_db"
    const val API_KEY = BuildConfig.API_KEY
    const val BASE_URL = "https://newsapi.org/v2/"
    val SOURCES = listOf("google-news-in", "bbc-news", "abc-news", "business-insider")

}