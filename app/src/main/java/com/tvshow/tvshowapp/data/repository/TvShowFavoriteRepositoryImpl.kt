package com.tvshow.tvshowapp.data.repository

import com.tvshow.tvshowapp.data.db.TvShowDao
import com.tvshow.tvshowapp.domain.model.db.TvShowFavoriteAttr
import com.tvshow.tvshowapp.domain.repository.TvShowFavoriteRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class TvShowFavoriteRepositoryImpl @Inject constructor(
    private val tvShowDao: TvShowDao
) : TvShowFavoriteRepository {

    override suspend fun insertTvShow(tvShow: TvShowFavoriteAttr) {
        tvShowDao.insertTvShow(tvShow = tvShow)
    }

    override fun getAllTvShows(): Flow<List<TvShowFavoriteAttr>> {
        return tvShowDao.getAllTvShows()
    }

    override suspend fun deleteTvShow(showId: String) {
        tvShowDao.deleteTvShow(showId = showId)
    }
}