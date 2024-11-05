package com.tvshow.tvshowapp.data.repository

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.tvshow.tvshowapp.data.network.TvShowService
import com.tvshow.tvshowapp.domain.model.TvShow
import com.tvshow.tvshowapp.util.UIError

class TvShowPagingSource(
    private val tvShowService: TvShowService
) : PagingSource<Int, TvShow>(){

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int,TvShow> {
        val page = params.key ?: 1

        return try {
            val response = tvShowService.getMostPopularTvShows(page)
            val tvShows = response.tvShows

            LoadResult.Page(
                data = tvShows,
                prevKey = if (page == 1) null else page - 1,
                nextKey = if (response.page >= response.pages) null else page + 1
            )
        } catch (exception: Exception) {
            val error =UIError(exception)

            LoadResult.Error(error)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, TvShow>): Int? {
        return state.anchorPosition?.let { position ->
            state.closestPageToPosition(position)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(position)?.nextKey?.minus(1)
        }
    }
}