package com.tvshow.tvshowapp.domain.repository

import androidx.paging.PagingData
import com.tvshow.tvshowapp.domain.model.response.TvShowHomeResponse
import com.tvshow.tvshowapp.domain.model.response.TvShowDetailResponse
import com.tvshow.tvshowapp.common.Response
import kotlinx.coroutines.flow.Flow

interface TvShowRepository {
    suspend fun getMostPopularTvShows(): Flow<PagingData<TvShowHomeResponse>>

    suspend fun getTvShowDetails(permaLink: String): Flow<Response<TvShowDetailResponse>>

    suspend fun getTvShowDetailsById(id: Int): Flow<Response<TvShowDetailResponse>>
}