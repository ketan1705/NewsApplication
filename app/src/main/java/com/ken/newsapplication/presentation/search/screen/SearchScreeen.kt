package com.ken.newsapplication.presentation.search.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.paging.compose.collectAsLazyPagingItems
import com.ken.newsapplication.domain.model.Article
import com.ken.newsapplication.presentation.common.ArticlesList
import com.ken.newsapplication.presentation.common.CustomSearchBar
import com.ken.newsapplication.presentation.navgraph.Route
import com.ken.newsapplication.presentation.search.SearchEvent
import com.ken.newsapplication.presentation.search.SearchState
import com.ken.newsapplication.util.Dimens.MediumPadding1

@Composable
fun SearchScreen(
    state: SearchState,
    onEvent: (SearchEvent) -> Unit,
    navigateToDetails: (Article) -> Unit,
) {
    Column(
        modifier = Modifier
            .padding(
                top = MediumPadding1, start = MediumPadding1,
                end = MediumPadding1
            )
            .statusBarsPadding()
            .fillMaxSize(),
    ) {
        CustomSearchBar(
            text = state.searchQuery,
            readOnly = false,
            onValueChange = { onEvent(SearchEvent.UpdateSearchQuery(it)) },
            onSearch = { onEvent(SearchEvent.SearchNews) }
        )

        Spacer(modifier = Modifier.height(MediumPadding1))

        state.articles?.let { article ->
            val articles = article.collectAsLazyPagingItems()
            ArticlesList(articles = articles, onClick = {
                navigateToDetails(it)
            })
        }

    }
}