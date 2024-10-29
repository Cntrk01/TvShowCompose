package com.tvshow.tvshowapp.domain.repository

import com.tvshow.tvshowapp.domain.model.TvShowModel
import com.tvshow.tvshowapp.domain.model.detail.TvShowDetail
import retrofit2.http.Query

interface TvShowRepository {
    suspend fun getMostPopularTvShows(page: Int = 1): TvShowModel

    suspend fun getTvShowDetails(permaLink: String): TvShowDetail
}