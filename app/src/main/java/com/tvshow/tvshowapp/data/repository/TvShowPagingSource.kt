package com.tvshow.tvshowapp.data.repository

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.tvshow.tvshowapp.data.network.TvShowService
import com.tvshow.tvshowapp.domain.model.response.TvShowHomeResponse
import com.tvshow.tvshowapp.core.CustomExceptions

class TvShowPagingSource(
    private val tvShowService: TvShowService
) : PagingSource<Int, TvShowHomeResponse>(){

    override val jumpingSupported: Boolean
        get() = true

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, TvShowHomeResponse> {
        val page = params.key ?: 1

        return try {
            val response = tvShowService.getMostPopularTvShows(page)
            val tvShows = response.tvShowHomeRespons

            LoadResult.Page(
                data = tvShows,
                prevKey = if (page == 1) null else page - 1,
                nextKey = if (response.page >= response.pages) null else page + 1
            )
        } catch (exception: Exception) {
            val error = CustomExceptions(exception)
            LoadResult.Error(error)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, TvShowHomeResponse>): Int? {
        return state.anchorPosition?.let { position ->
            state.closestPageToPosition(position)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(position)?.nextKey?.minus(1)

        }
    }
}