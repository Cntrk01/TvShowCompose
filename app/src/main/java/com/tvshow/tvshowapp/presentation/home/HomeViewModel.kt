package com.tvshow.tvshowapp.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.tvshow.tvshowapp.data.mapper.toShowMapper
import com.tvshow.tvshowapp.domain.model.attr.TvShowHomeAttr
import com.tvshow.tvshowapp.domain.repository.TvShowRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: TvShowRepository
) : ViewModel() {

    private val _tvShowPagingData = MutableStateFlow<PagingData<TvShowHomeAttr>>(PagingData.empty())
    val tvShowPagingData: StateFlow<PagingData<TvShowHomeAttr>> = _tvShowPagingData

    init {
        getTvShows()
    }

    private fun getTvShows() = viewModelScope.launch(Dispatchers.IO) {
        repository.getMostPopularTvShows()
            .cachedIn(viewModelScope)
            .collectLatest { pagingData ->
                _tvShowPagingData.value = pagingData.map { it.toShowMapper() }
            }
    }
}