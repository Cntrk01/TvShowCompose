package com.tvshow.tvshowapp.domain.repository

import com.tvshow.tvshowapp.domain.model.db.TvShowFavoriteAttr
import kotlinx.coroutines.flow.Flow

interface TvShowFavoriteRepository {
    suspend fun insertTvShow(tvShow: TvShowFavoriteAttr)
    fun getAllTvShows(): Flow<List<TvShowFavoriteAttr>>
    suspend fun deleteTvShow(showId: String)
    suspend fun isSavedTvShow(showId: String) : Boolean
}