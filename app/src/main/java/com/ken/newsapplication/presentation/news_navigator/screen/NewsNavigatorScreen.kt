package com.ken.newsapplication.presentation.news_navigator.screen

import android.widget.Toast
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.paging.compose.collectAsLazyPagingItems
import com.ken.newsapplication.R
import com.ken.newsapplication.domain.model.Article
import com.ken.newsapplication.presentation.bookmark.screen.BookmarkScreen
import com.ken.newsapplication.presentation.bookmark.viewmodel.BookmarkViewModel
import com.ken.newsapplication.presentation.details.DetailScreen
import com.ken.newsapplication.presentation.details.DetailsEvent
import com.ken.newsapplication.presentation.details.viewmodel.DetailViewModel
import com.ken.newsapplication.presentation.home.screen.HomeScreen
import com.ken.newsapplication.presentation.home.viewmodel.HomeViewModel
import com.ken.newsapplication.presentation.navgraph.Route
import com.ken.newsapplication.presentation.news_navigator.components.BottomNavigationItem
import com.ken.newsapplication.presentation.news_navigator.components.NewsBottomNavigation
import com.ken.newsapplication.presentation.search.SearchViewModel
import com.ken.newsapplication.presentation.search.screen.SearchScreen

@Composable
fun NewsNavigatorScreen() {

    val bottomNavigationItems = remember {
        listOf(
            BottomNavigationItem(
                icon = R.drawable.ic_home,
                text = "Home"
            ),
            BottomNavigationItem(
                icon = R.drawable.ic_search,
                text = "Search"
            ),
            BottomNavigationItem(
                icon = R.drawable.ic_bookmark,
                text = "Bookmark"
            )
        )
    }

    val navController = rememberNavController()
    val backStackState = navController.currentBackStackEntryAsState().value
    var selectedItem by remember { mutableIntStateOf(0) }
    var isBottomBarVisible = remember(key1 = backStackState) {
        backStackState?.destination?.route == Route.HomeScreen.route ||
                backStackState?.destination?.route == Route.SearchScreen.route ||
                backStackState?.destination?.route == Route.BookmarkScreen.route
    }

    selectedItem = remember(key1 = backStackState) {
        when (backStackState?.destination?.route) {
            Route.HomeScreen.route -> 0
            Route.SearchScreen.route -> 1
            Route.BookmarkScreen.route -> 2
            else -> 0
        }
    }
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            if (isBottomBarVisible) {

                NewsBottomNavigation(
                    items = bottomNavigationItems,
                    selectedItem = selectedItem,
                    onItemClick = { index ->
                        when (index) {
                            0 -> navigateToTap(navController, Route.HomeScreen.route)
                            1 -> navigateToTap(navController, Route.SearchScreen.route)
                            2 -> navigateToTap(navController, Route.BookmarkScreen.route)
                        }
                    }
                )
            }
        }
    ) {
        val bottomPadding = it.calculateBottomPadding()
        NavHost(
            navController = navController,
            startDestination = Route.HomeScreen.route,
            modifier = Modifier.padding(bottom = bottomPadding)
        ) {
            composable(
                route = Route.HomeScreen.route,
                enterTransition = {
                    fadeIn(animationSpec = tween(durationMillis = 300))
                },
                exitTransition = {
                    fadeOut(animationSpec = tween(durationMillis = 300))
                },
                popEnterTransition = {
                    fadeIn(animationSpec = tween(durationMillis = 300))
                },
                popExitTransition = {
                    fadeOut(animationSpec = tween(durationMillis = 300))
                }) {
                val viewModel: HomeViewModel = hiltViewModel()
                val articles = viewModel.news.collectAsLazyPagingItems()
                HomeScreen(
                    articles = articles,
                    navigateToSearch = {
                        navigateToTap(navController, Route.SearchScreen.route)
                    },
                    navigateToDetail = { article ->
                        navigateToDetail(navController, article = article)
                    },
                )
            }

            composable(
                route = Route.SearchScreen.route,
                enterTransition = {
                    fadeIn(animationSpec = tween(durationMillis = 300))
                },
                exitTransition = {
                    fadeOut(animationSpec = tween(durationMillis = 300))
                },
                popEnterTransition = {
                    fadeIn(animationSpec = tween(durationMillis = 300))
                },
                popExitTransition = {
                    fadeOut(animationSpec = tween(durationMillis = 300))
                }) {
                val viewModel: SearchViewModel = hiltViewModel()
                val state = viewModel.state.value

                SearchScreen(
                    state = state,
                    onEvent = viewModel::onEvent,
                    navigateToDetails = { article ->
                        navigateToDetail(navController, article = article)
                    }
                )
            }

            composable(
                route = Route.DetailsScreen.route,
                enterTransition = {
                    slideInHorizontally(
                        initialOffsetX = { fullWidth -> fullWidth }, // Slide in from the right
                        animationSpec = tween(durationMillis = 300)
                    ) + fadeIn(animationSpec = tween(durationMillis = 300))
                },
                exitTransition = {
                    slideOutHorizontally(
                        targetOffsetX = { fullWidth -> fullWidth }, // Slide out to the right
                        animationSpec = tween(durationMillis = 300)
                    ) + fadeOut(animationSpec = tween(durationMillis = 300))
                },
                popEnterTransition = {
                    slideInHorizontally(
                        initialOffsetX = { fullWidth -> -fullWidth }, // Slide in from the left
                        animationSpec = tween(durationMillis = 300)
                    ) + fadeIn(animationSpec = tween(durationMillis = 300))
                },
                popExitTransition = {
                    slideOutHorizontally(
                        targetOffsetX = { fullWidth -> -fullWidth }, // Slide out to the left
                        animationSpec = tween(durationMillis = 300)
                    ) + fadeOut(animationSpec = tween(durationMillis = 300))
                }) {
                val viewModel: DetailViewModel = hiltViewModel()
                if (viewModel.sideEffect != null) {
                    Toast.makeText(LocalContext.current, viewModel.sideEffect, Toast.LENGTH_SHORT)
                        .show()
                    viewModel.onEvent(DetailsEvent.RemoveSideEffect)
                }
                navController.previousBackStackEntry?.savedStateHandle?.get<Article?>("article")
                    ?.let { article ->
                        DetailScreen(
                            article = article,
                            event = viewModel::onEvent,
                            navigateUp = { navController.navigateUp() }
                        )
                    }
            }

            composable(
                route = Route.BookmarkScreen.route,
                enterTransition = {
                    fadeIn(animationSpec = tween(durationMillis = 300))
                },
                exitTransition = {
                    fadeOut(animationSpec = tween(durationMillis = 300))
                },
                popEnterTransition = {
                    fadeIn(animationSpec = tween(durationMillis = 300))
                },
                popExitTransition = {
                    fadeOut(animationSpec = tween(durationMillis = 300))
                }) {
                val viewModel: BookmarkViewModel = hiltViewModel()
                val state = viewModel.state.value

                BookmarkScreen(
                    state = state,
                    navigateToDetails = { article ->
                        navigateToDetail(navController, article = article)
                    }
                )
            }
        }
    }
}

private fun navigateToTap(navController: NavController, route: String) {
    navController.navigate(route = route)
    {
        navController.graph.startDestinationRoute?.let { homeScreen ->
            popUpTo(homeScreen) {
                saveState = true
            }
            restoreState = true
            launchSingleTop = true
        }
    }
}

private fun navigateToDetail(navController: NavController, article: Article) {
    navController.currentBackStackEntry?.savedStateHandle?.set("article", article)
    navController.navigate(route = Route.DetailsScreen.route)
}