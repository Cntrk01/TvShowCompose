package com.tvshow.tvshowapp.domain.usecase

import com.tvshow.tvshowapp.domain.model.response.TvShowDetailResponse
import com.tvshow.tvshowapp.domain.repository.TvShowRepository
import com.tvshow.tvshowapp.core.CustomExceptions
import com.tvshow.tvshowapp.common.Response
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class DetailPageUseCase @Inject
    constructor(private val repository: TvShowRepository){

    suspend operator fun invoke(showId: Any): Flow<Response<TvShowDetailResponse>> = flow {
        when (showId) {
            is String -> emitAll(repository.getTvShowDetails(permaLink = showId))
            is Int -> emitAll(repository.getTvShowDetailsById(id = showId))
            else -> emit(Response.Error(error = CustomExceptions(customErrorMessage = "Unknown Error")))
        }
    }
}