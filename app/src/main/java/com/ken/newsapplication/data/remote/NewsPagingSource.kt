package com.ken.newsapplication.data.remote.dto

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.ken.newsapplication.data.remote.NewsAPI
import com.ken.newsapplication.domain.model.Article
import com.ken.newsapplication.util.Constants
import com.ken.newsapplication.util.Constants.API_KEY

class NewsPagingSource(
    private val newsApi: NewsAPI,
    private val sources: String,
) : PagingSource<Int, Article>() {

    private var totalNewsCount = 0

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Article> {
        val page = params.key ?: 1
        return try {
            val response = newsApi.getNews(
                sources = sources, page = page,
                apiKey = API_KEY
            )
            Log.d(Constants.TAG, "Page: $page ---> Articles size: ${response.articles.size}")
            totalNewsCount += response.articles.size
            Log.d(Constants.TAG, "Total news count: $totalNewsCount")
            val articles = response.articles.distinctBy {
                it.title
            }// Remove Duplicates
            LoadResult.Page(
                data = articles,
                prevKey = null,
                nextKey = if (totalNewsCount == response.totalResults) null else page + 1
            )
        } catch (e: Exception) {
            e.printStackTrace()
            // 👇 If some data was already loaded, return an empty page instead of an error
            if (totalNewsCount > 0) {
                Log.d(
                    Constants.TAG,
                    "Error occurred but partial data was already loaded. Returning empty page."
                )
                LoadResult.Page(
                    data = emptyList(),
                    prevKey = null,
                    nextKey = null // You could keep pagination if needed
                )
            } else {
                LoadResult.Error(e)
            }
//            return LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Article>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }
}