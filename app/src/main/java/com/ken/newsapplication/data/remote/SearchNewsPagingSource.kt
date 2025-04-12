package com.ken.newsapplication.data.remote

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.ken.newsapplication.domain.model.Article
import com.ken.newsapplication.util.Constants.API_KEY

class SearchNewsPagingSource(
    private val newsApi: NewsAPI,
    private val searchQuery: String,
    private val sources: String,
) : PagingSource<Int, Article>() {

    private var totalNewsCount = 0

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Article> {
        val page = params.key ?: 1
        return try {
            val response = newsApi.searchNews(
                searchQuery = searchQuery,
                sources = sources, page = page,
                apiKey = API_KEY
            )
            totalNewsCount += response.articles.size
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
            return LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Article>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }


}