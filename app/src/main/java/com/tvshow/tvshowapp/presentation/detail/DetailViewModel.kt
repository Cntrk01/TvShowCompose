package com.tvshow.tvshowapp.presentation.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tvshow.tvshowapp.domain.model.detail.TvShowDetail
import com.tvshow.tvshowapp.domain.repository.TvShowRepository
import com.tvshow.tvshowapp.util.Response
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val tvShowService: TvShowRepository,
    private val savedStateHandle: SavedStateHandle,
) : ViewModel() {

    private val _tvShow = MutableStateFlow(DetailPageState())
    val tvShow: StateFlow<DetailPageState> get() = _tvShow

    init {
        savedStateHandle.get<Any>(key = "detailId")?.let { tvShow ->
            getTvShow(showId = tvShow)
        }
    }

    private fun getTvShow(showId: Any) {
        when (showId) {
            is String -> {
                getTvShowDetailString(name = showId)
            }

            is Int -> {
                getTvShowDetailId(id = showId)
            }
        }
    }

    private fun getTvShowDetailId(id: Int) = viewModelScope.launch(Dispatchers.IO) {
        val getDetailId = tvShowService.getTvShowDetailsById(id = id)
        handleTvShowResponse(getDetailId)
    }

    private fun getTvShowDetailString(name: String) = viewModelScope.launch(Dispatchers.IO) {
        val getShowLink = tvShowService.getTvShowDetails(permaLink = name)
        handleTvShowResponse(getShowLink)
    }

    private suspend fun handleTvShowResponse(response: Flow<Response<TvShowDetail>>) {
        response.collectLatest { responseValue ->
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
                    error = responseValue.message ?: "Unknown error",
                    tvShow = null
                )

                is Response.Success -> {
                    val tvShowData = responseValue.data?.tvShow
                    if (tvShowData != null) {
                        _tvShow.value =_tvShow.value.copy(
                            loading = false,
                            error = "",
                            tvShow = tvShowData
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

}
