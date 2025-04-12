package com.ken.newsapplication.presentation.onboarding

import androidx.annotation.DrawableRes
import com.ken.newsapplication.R

data class Page(
    val title: String,
    val description: String,
    @DrawableRes val image: Int,
)

val pages = listOf<Page>(
    Page(
        title = "Welcome to News App",
        description = "Discover the latest news and updates from around the world.",
        image = R.drawable.onboarding1
    ),
    Page(
        title = "Stay Informed",
        description = "Get access to the latest news and updates from around the world.",
        image = R.drawable.onboarding2
    ),
    Page(
        title = "Explore",
        description = "Explore the latest news and updates from around the world.",
        image = R.drawable.onboarding3
    )
)