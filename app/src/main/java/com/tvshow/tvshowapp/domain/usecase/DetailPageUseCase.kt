package com.tvshow.tvshowapp.domain.usecase

import com.tvshow.tvshowapp.common.response.Response
import com.tvshow.tvshowapp.common.exceptions.CustomExceptions
import com.tvshow.tvshowapp.data.mapper.toTvShowDetailAttr
import com.tvshow.tvshowapp.data.mapper.toTvShowFavorite
import com.tvshow.tvshowapp.domain.model.attr.TvShowDetailAttr
import com.tvshow.tvshowapp.domain.model.response.TvShowDetailResponse
import com.tvshow.tvshowapp.domain.repository.TvShowFavoriteRepository
import com.tvshow.tvshowapp.domain.repository.TvShowServiceRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class DetailPageUseCase @Inject
constructor(
    private val repository: TvShowServiceRepository,
    private val favoriteRepository: TvShowFavoriteRepository,
) {
    suspend fun getDetailPageData(showId: Any): Flow<Response<TvShowDetailResponse>> = flow {
        when (showId) {
            is Int -> emitAll(repository.getTvShowDetailsById(id = showId))
            is String -> emitAll(repository.getTvShowDetails(permaLink = showId))
            else -> emit(Response.Error(error = CustomExceptions(customErrorMessage = "Unknown Error")))
        }
    }

    suspend fun getItemFromDb(showId: String) : TvShowDetailAttr? {
        return favoriteRepository.getItemFromDb(showId = showId)?.toTvShowDetailAttr()
    }

    suspend fun starItemFavorite(tvShow: TvShowDetailAttr): Boolean {
        val isFavorite = tvShow.id?.let { favoriteRepository.isSavedTvShow(showId = it) } ?: false

        if (isFavorite) {
            tvShow.id?.let { favoriteRepository.deleteTvShow(showId = it) }
        } else {
            favoriteRepository.insertTvShow(tvShow.toTvShowFavorite())
        }
        return !isFavorite
    }

    suspend fun checkIsItemFavorite(showId : String) : Boolean{
        return favoriteRepository.isSavedTvShow(showId = showId)
    }
}