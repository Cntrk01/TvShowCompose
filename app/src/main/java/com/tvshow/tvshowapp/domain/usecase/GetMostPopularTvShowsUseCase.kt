package com.tvshow.tvshowapp.domain.usecase

import androidx.paging.PagingData
import androidx.paging.map
import com.tvshow.tvshowapp.data.mapper.TvShowMainPage
import com.tvshow.tvshowapp.data.mapper.toShowMapper
import com.tvshow.tvshowapp.domain.repository.TvShowRepository
import com.tvshow.tvshowapp.util.Response
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetMostPopularTvShowsUseCase(
    private val repository: TvShowRepository
) {
    suspend operator fun invoke(page: Int): Flow<Response<PagingData<List<TvShowMainPage>>>> {
        return repository.getMostPopularTvShows(page).map { response ->
            when (response) {
                is Response.Loading -> Response.Loading()
                is Response.Error -> Response.Error(response.message.toString(), response.cause)
                is Response.Success -> {
                    val mappedData = response.data?.map { pagingData ->
                        pagingData.map { tvShow ->
                            tvShow.toShowMapper()
                        }
                    }
                    Response.Success(mappedData)
                }
            }
        }
    }
}