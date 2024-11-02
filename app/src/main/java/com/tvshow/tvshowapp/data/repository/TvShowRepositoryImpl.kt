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
import com.tvshow.tvshowapp.util.UIError
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class TvShowRepositoryImpl(
    private val tvShowService: TvShowService
) : TvShowRepository {
    override suspend fun getMostPopularTvShows(page: Int): Flow<Response<PagingData<List<TvShow>>>> {
        return flow {
            emit(Response.Loading())
            try {
                Pager(
                    config = PagingConfig(pageSize = 10),
                    pagingSourceFactory = { TvShowPagingSource(tvShowService) }
                ).flow
            }catch (e: Exception){
                val exception = UIError(e)
                emit(Response.Error(exception.message,exception.cause))
            }
        }
    }

    override suspend fun getTvShowDetails(permaLink: String): LiveData<Response<TvShowDetail>> {
        return liveData {
            emit(Response.Loading())
            try {
                val response = tvShowService.getTvShowDetails(permaLink)
                emit(Response.Success(response))
            } catch (e: Exception) {
                emit(Response.Error(e.message.toString(),e.cause))
            }
        }
    }
}