package com.tvshow.tvshowapp.domain.usecase

import androidx.paging.PagingData
import androidx.paging.map
import com.tvshow.tvshowapp.data.mapper.TvShowHomePage
import com.tvshow.tvshowapp.data.mapper.toShowMapper
import com.tvshow.tvshowapp.domain.repository.TvShowRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetMostPopularTvShowsUseCase @Inject constructor(
    private val repository: TvShowRepository
) {
    suspend operator fun invoke(): Flow<PagingData<TvShowHomePage>> {
        return repository.getMostPopularTvShows().map { response ->
            response.map { pagingData ->
                pagingData.toShowMapper()
            }
        }
    }
}