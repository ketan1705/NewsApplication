package com.ken.newsapplication.presentation.details

import android.content.Intent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.net.toUri
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.ken.newsapplication.R
import com.ken.newsapplication.domain.model.Article
import com.ken.newsapplication.domain.model.Source
import com.ken.newsapplication.presentation.details.components.DetailsTopBar
import com.ken.newsapplication.ui.theme.NewsApplicationTheme
import com.ken.newsapplication.util.Dimens.ArticleImageHeight
import com.ken.newsapplication.util.Dimens.MediumPadding1

@Composable
fun DetailScreen(
    article: Article,
    event: (DetailsEvent) -> Unit,
    navigateUp: () -> Unit,
) {

    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
    ) {
        DetailsTopBar(
            onBrowsingClick = {
                Intent(Intent.ACTION_VIEW).also {
                    it.data = article.url.toUri()
                    if (it.resolveActivity(context.packageManager) != null) {
                        context.startActivity(it)
                    }
                }
            },
            onShareClick = {
                Intent(Intent.ACTION_SEND).also {
                    it.putExtra(Intent.EXTRA_TEXT, article.url)
                    it.type = "text/plain"
                    if (it.resolveActivity(context.packageManager) != null) {
                        context.startActivity(it)
                    }
                }
            },
            onBoomarkClick = {
                event(DetailsEvent.InsertDeleteArticle(article))
            },
            onBackClick = {
                navigateUp()
            }
        )


        LazyColumn(
            modifier = Modifier.fillMaxWidth(),
            contentPadding = PaddingValues(
                start = MediumPadding1,
                end = MediumPadding1,
                top = MediumPadding1
            )
        ) {

            item {
                AsyncImage(
                    model = ImageRequest.Builder(context = context)
                        .data(article.urlToImage)
                        .build(),
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(ArticleImageHeight)
                        .clip(MaterialTheme.shapes.medium),
                    contentScale = ContentScale.Crop
                )

                Spacer(modifier = Modifier.height(MediumPadding1))

                Text(
                    text = article.title,
                    style = MaterialTheme.typography.displaySmall,
                    color = colorResource(R.color.text_title)

                )
                Text(
                    text = article.content,
                    style = MaterialTheme.typography.bodyMedium,
                    color = colorResource(R.color.body)

                )

            }

        }

    }
}

@Preview(showBackground = true)
//@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun DetailScreenPrev() {
    NewsApplicationTheme {
        DetailScreen(
            article = Article(
                title = "Apple becomes world's most valuable company",
                author = "BBC News",
                content =
                    "Apple has overtaken ExxonMobil to become the world's most valuable company, as investors responded positively to its quarterly results. The iPhone maker’s stock surged by nearly 8% in after-hours trading, taking its market value to more than $620bn.",
                description =
                    "Apple's market value surpasses ExxonMobil's, making it the most valuable company in the world.",
                publishedAt = "2011-08-10T17:05:00Z",
                source = Source(id = "bbc-news", name = "BBC News"),
                url = "https://www.bbc.com/news/business-13200758",
                urlToImage = "https://ichef.bbci.co.uk/ace/standard/976/cpsprodpb/A237/production/_130372514_col_gdp_976-nc.png.webp"
            ),
            event = TODO()
        ) { }
    }
}