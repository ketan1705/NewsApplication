package com.ken.newsapplication.presentation.news_navigator.components

import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ken.newsapplication.R
import com.ken.newsapplication.ui.theme.NewsApplicationTheme
import com.ken.newsapplication.util.Dimens.ExtraSmallPadding2
import com.ken.newsapplication.util.Dimens.IconSize

@Composable
fun NewsBottomNavigation(
    items: List<BottomNavigationItem>,
    selectedItem: Int,
    onItemClick: (Int) -> Unit,
) {

    NavigationBar(
        modifier = Modifier.fillMaxWidth(),
        containerColor = MaterialTheme.colorScheme.background,
        tonalElevation = 10.dp
    ) {
        items.forEachIndexed { index, item ->
            NavigationBarItem(
                selected = selectedItem == index,
                onClick = { onItemClick(index) },
                icon = {
                    Column(
                        horizontalAlignment = CenterHorizontally
                    ) {
                        Icon(
                            painter = painterResource(item.icon),
                            contentDescription = null,
                            modifier = Modifier.size(IconSize)
                        )

                        Spacer(modifier = Modifier.height(ExtraSmallPadding2))

                        Text(
                            text = item.text,
                            style = MaterialTheme.typography.labelSmall
                        )

                    }
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = MaterialTheme.colorScheme.primary,
                    selectedTextColor = MaterialTheme.colorScheme.primary,
                    indicatorColor = MaterialTheme.colorScheme.background,
                    unselectedIconColor = colorResource(R.color.body),
                    unselectedTextColor = colorResource(R.color.body)
                )
            )
        }

    }

}

data class BottomNavigationItem(
    @DrawableRes val icon: Int,
    val text: String,
)

@Preview
@Preview(uiMode = android.content.res.Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun NewsBottomNavigationPrev() {
    NewsApplicationTheme {
        NewsBottomNavigation(
            items = listOf(
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
            ),
            selectedItem = 0,
            onItemClick = {}
        )
    }
}
