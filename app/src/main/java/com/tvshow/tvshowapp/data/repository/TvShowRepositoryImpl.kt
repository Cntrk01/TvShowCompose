package com.tvshow.tvshowapp.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.tvshow.tvshowapp.data.network.TvShowService
import com.tvshow.tvshowapp.domain.model.response.TvShowHomeResponse
import com.tvshow.tvshowapp.domain.model.response.TvShowDetailResponse
import com.tvshow.tvshowapp.domain.repository.TvShowRepository
import com.tvshow.tvshowapp.util.CustomExceptions
import com.tvshow.tvshowapp.util.Response
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class TvShowRepositoryImpl(
    private val tvShowService: TvShowService
) : TvShowRepository {
    override suspend fun getMostPopularTvShows(): Flow<PagingData<TvShowHomeResponse>> {
        return Pager(
            config = PagingConfig(pageSize = 10),
            pagingSourceFactory = { TvShowPagingSource(tvShowService) }
        ).flow
    }

    override suspend fun getTvShowDetails(permaLink: String): Flow<Response<TvShowDetailResponse>> {
        return flow {
            emit(Response.Loading())
            try {
                val response = tvShowService.getTvShowDetails(permaLink)
                emit(Response.Success(response))
            } catch (e: Exception) {
                emit(Response.Error(CustomExceptions(e)))
            }
        }
    }

    override suspend fun getTvShowDetailsById(id: Int): Flow<Response<TvShowDetailResponse>> {
        return flow {
            emit(Response.Loading())
            try {
                val response = tvShowService.getTvShowDetailsById(id)
                emit(Response.Success(response))
            } catch (e: Exception) {
                emit(Response.Error(CustomExceptions(e)))
            }
        }
    }
}