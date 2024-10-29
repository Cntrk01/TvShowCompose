package com.tvshow.tvshowapp.data.network

import com.tvshow.tvshowapp.domain.model.TvShowModel
import com.tvshow.tvshowapp.domain.model.detail.TvShowDetail
import retrofit2.http.GET
import retrofit2.http.Query

interface TvShowService {
    @GET("most-popular")
    suspend fun getMostPopularTvShows(
        @Query("page") page: Int = 1
    ): TvShowModel

    @GET("show-details")
    suspend fun getTvShowDetails(
        @Query("q") permaLink: String
    ): TvShowDetail
}