package com.tvshow.tvshowapp.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.tvshow.tvshowapp.data.network.TvShowService
import com.tvshow.tvshowapp.domain.model.TvShow
import com.tvshow.tvshowapp.domain.model.detail.TvShowDetail
import com.tvshow.tvshowapp.domain.repository.TvShowRepository
import com.tvshow.tvshowapp.util.Response
import kotlinx.coroutines.flow.Flow

class TvShowRepositoryImpl(
    private val tvShowService: TvShowService
) : TvShowRepository {
    override suspend fun getMostPopularTvShows(): Flow<PagingData<TvShow>> {
        return Pager(
            config = PagingConfig(pageSize = 10),
            pagingSourceFactory = { TvShowPagingSource(tvShowService) }
        ).flow
    }

    override suspend fun getTvShowDetails(permaLink: String): LiveData<Response<TvShowDetail>> {
        return liveData {
            emit(Response.Loading())
            try {
                val response = tvShowService.getTvShowDetails(permaLink)
                emit(Response.Success(response))
            } catch (e: Exception) {
                emit(Response.Error(e.message.toString(), e.cause))
            }
        }
    }
}