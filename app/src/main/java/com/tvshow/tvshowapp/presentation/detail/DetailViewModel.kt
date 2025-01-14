package com.tvshow.tvshowapp.presentation.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tvshow.tvshowapp.data.mapper.toTvShowDescription
import com.tvshow.tvshowapp.domain.usecase.DetailPageUseCase
import com.tvshow.tvshowapp.common.Response
import com.tvshow.tvshowapp.data.mapper.toTvShowFavorite
import com.tvshow.tvshowapp.domain.model.attr.TvShowDetailAttr
import com.tvshow.tvshowapp.domain.repository.TvShowFavoriteRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val detailPageUseCase: DetailPageUseCase,
    private val tvShowFavoriteRepository: TvShowFavoriteRepository,
) : ViewModel() {

    private val _tvShow = MutableStateFlow(DetailPageState())
    val tvShow: StateFlow<DetailPageState> get() = _tvShow
    private var tvShowId : Any = ""

    init {
        tvShowId = savedStateHandle.get<Any>(key = "detailId") ?: ""
        handleTvShowResponse()
    }

    fun retryGetTvShow() = viewModelScope.launch(Dispatchers.IO){
        handleTvShowResponse()
    }

    private fun handleTvShowResponse() = viewModelScope.launch(Dispatchers.IO){
        detailPageUseCase.getDetailPageData(showId = tvShowId).collectLatest { responseValue ->
            when (responseValue) {
                is Response.Loading ->
                    _tvShow.value = _tvShow.value.copy(
                        loading = true,
                        error = "",
                        tvShow = null
                )

                is Response.Error ->
                    _tvShow.value =_tvShow.value.copy(
                        loading = false,
                        error = responseValue.error?.message ?: "Unknown Error",
                        tvShow = null,
                        isShowAction = responseValue.error?.isShowAction ?: false
                )

                is Response.Success -> {
                    val tvShowData = responseValue.data
                    
                    if (tvShowData != null) {
                        _tvShow.value =_tvShow.value.copy(
                            loading = false,
                            error = "",
                            tvShow = tvShowData.tvShow.toTvShowDescription()
                        )
                    } else {
                        _tvShow.value = _tvShow.value.copy(
                            loading = false,
                            error = "No TV Show data found",
                            tvShow = null
                        )
                    }
                }
            }
        }
    }

    fun toggleFavorite(tvShow: TvShowDetailAttr) = viewModelScope.launch(Dispatchers.IO) {
        val newFavoriteState = detailPageUseCase.toggleTvShowFavorite(tvShow)
        _tvShow.value = _tvShow.value.copy(isSaved = newFavoriteState)
    }
}