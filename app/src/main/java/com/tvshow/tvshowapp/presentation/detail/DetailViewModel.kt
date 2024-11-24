package com.tvshow.tvshowapp.presentation.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tvshow.tvshowapp.domain.model.detail.TvShow
import com.tvshow.tvshowapp.domain.model.detail.TvShowDetail
import com.tvshow.tvshowapp.domain.repository.TvShowRepository
import com.tvshow.tvshowapp.navigation.Route
import com.tvshow.tvshowapp.util.Response
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val tvShowService: TvShowRepository,
    private val savedStateHandle: SavedStateHandle,
) : ViewModel() {

    private val _tvShow = MutableLiveData(DetailPageState())
    val tvShow: LiveData<DetailPageState> = _tvShow

    init {
        savedStateHandle.get<Any>(key = "detailId")?.let { tvShow ->
            println(tvShow)
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

    private fun getTvShowDetailId(id: Int) = viewModelScope.launch(Dispatchers.Main) {
        tvShowService.getTvShowDetailsById(id = id).observeForever { response ->
            handleTvShowResponse(response)
        }
    }

    private fun getTvShowDetailString(name: String) = viewModelScope.launch(Dispatchers.Main) {
        tvShowService.getTvShowDetails(permaLink = name).observeForever { response ->
            handleTvShowResponse(response)
        }
    }

    private fun handleTvShowResponse(response: Response<TvShowDetail>) {
        _tvShow.value = when (response) {
            is Response.Loading -> _tvShow.value?.copy(
                loading = true,
                error = "",
                tvShow = null
            )

            is Response.Error -> _tvShow.value?.copy(
                loading = false,
                error = response.message ?: "Unknown error",
                tvShow = null
            )

            is Response.Success -> {
                val tvShowData = response.data?.tvShow
                if (tvShowData != null) {
                    _tvShow.value?.copy(
                        loading = false,
                        error = "",
                        tvShow = tvShowData
                    )
                } else {
                    _tvShow.value?.copy(
                        loading = false,
                        error = "",
                        tvShow = null
                    )
                }
            }
        }
    }
}