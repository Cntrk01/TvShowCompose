package com.tvshow.tvshowapp.domain.repository

import androidx.lifecycle.LiveData
import androidx.paging.PagingData
import com.tvshow.tvshowapp.domain.model.TvShow
import com.tvshow.tvshowapp.domain.model.detail.TvShowDetail
import com.tvshow.tvshowapp.util.Response
import kotlinx.coroutines.flow.Flow

interface TvShowRepository {
    suspend fun getMostPopularTvShows(): Flow<PagingData<TvShow>>

    suspend fun getTvShowDetails(permaLink: String): LiveData<Response<TvShowDetail>>

    suspend fun getTvShowDetailsById(id: Int): LiveData<Response<TvShowDetail>>
}