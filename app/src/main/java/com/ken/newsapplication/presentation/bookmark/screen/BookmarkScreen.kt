package com.ken.newsapplication.presentation.bookmark.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import com.ken.newsapplication.R
import com.ken.newsapplication.domain.model.Article
import com.ken.newsapplication.presentation.bookmark.BookmarkState
import com.ken.newsapplication.presentation.common.ArticlesList
import com.ken.newsapplication.util.Dimens.MediumPadding1

@Composable
fun BookmarkScreen(
    state: BookmarkState,
    navigateToDetails: (Article) -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
            .padding(
                top = MediumPadding1,
                start = MediumPadding1,
                end = MediumPadding1,
            ),
    ) {

        Text(
            text = "Bookmark",
            style = MaterialTheme.typography.displaySmall
                .copy(fontWeight = FontWeight.Bold),
            color = colorResource(R.color.text_title)
        )

        Spacer(modifier = Modifier.height(MediumPadding1))
        ArticlesList(articles = state.articles, onClick = { navigateToDetails(it) })

    }

}