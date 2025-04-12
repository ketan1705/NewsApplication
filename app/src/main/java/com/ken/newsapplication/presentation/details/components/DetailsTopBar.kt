package com.ken.newsapplication.presentation.details.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.ken.newsapplication.R
import com.ken.newsapplication.ui.theme.NewsApplicationTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailsTopBar(
    onBrowsingClick: () -> Unit,
    onShareClick: () -> Unit,
    onBoomarkClick: () -> Unit,
    onBackClick: () -> Unit,
) {

    TopAppBar(
        title = {
        },
        modifier = Modifier.fillMaxWidth(),
        colors = TopAppBarDefaults.mediumTopAppBarColors(
            containerColor = Color.Transparent,
            actionIconContentColor = colorResource(R.color.body),
            navigationIconContentColor = colorResource(R.color.body)
        ),
        navigationIcon = {
            IconButton(
                onClick = onBackClick
            ) {
                Icon(
                    painter = painterResource(R.drawable.ic_back_arrow),
                    contentDescription = "Back"
                )
            }
        },
        actions = {
            IconButton(
                onClick = onBoomarkClick
            ) {
                Icon(
                    painter = painterResource(R.drawable.ic_bookmark),
                    contentDescription = "Bookmark"
                )
            }
            IconButton(
                onClick = onShareClick
            ) {
                Icon(
                    imageVector = Icons.Default.Share,
                    contentDescription = "Share"
                )
            }
            IconButton(
                onClick = onBrowsingClick
            ) {
                Icon(
                    painter = painterResource(R.drawable.ic_network),
                    contentDescription = "Network"
                )
            }
        }

    )

}

@Preview(showBackground = true)
@Preview(showBackground = true, uiMode = android.content.res.Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun DetailTopBarPrev() {
    NewsApplicationTheme {
        Box(modifier = Modifier.background(MaterialTheme.colorScheme.background))
        {

            DetailsTopBar(
                onBrowsingClick = {},
                onShareClick = {},
                onBoomarkClick = {},
                onBackClick = {}
            )
        }
    }
}