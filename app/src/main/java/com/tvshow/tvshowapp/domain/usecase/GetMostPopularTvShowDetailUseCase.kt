package com.tvshow.tvshowapp.domain.usecase

import androidx.lifecycle.LiveData
import com.tvshow.tvshowapp.domain.model.detail.TvShowDetail
import com.tvshow.tvshowapp.domain.repository.TvShowRepository
import com.tvshow.tvshowapp.util.Response
import javax.inject.Inject

class GetMostPopularTvShowDetailUseCase @Inject constructor(
    private val repository: TvShowRepository
) {
    suspend operator fun invoke(permaLink: String): LiveData<Response<TvShowDetail>> {
        return repository.getTvShowDetails(permaLink)
    }
}