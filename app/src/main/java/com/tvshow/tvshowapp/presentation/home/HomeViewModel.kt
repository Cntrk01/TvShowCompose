package com.tvshow.tvshowapp.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.tvshow.tvshowapp.data.mapper.TvShowHomePage
import com.tvshow.tvshowapp.domain.usecase.GetMostPopularTvShowsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getMostPopularTvShowsUseCase: GetMostPopularTvShowsUseCase,
) : ViewModel() {

    private val _tvShowPagingData = MutableStateFlow<PagingData<TvShowHomePage>>(PagingData.empty())
    val tvShowPagingData: StateFlow<PagingData<TvShowHomePage>> = _tvShowPagingData

    init {
        getTvShows()
    }

    private fun getTvShows() = viewModelScope.launch(Dispatchers.IO) {
        getMostPopularTvShowsUseCase()
            .cachedIn(viewModelScope)
            .collect { pagingData ->
                _tvShowPagingData.value = pagingData
            }
    }
}