package com.tvshow.tvshowapp.presentation.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tvshow.tvshowapp.domain.model.detail.TvShowDetail
import com.tvshow.tvshowapp.domain.repository.TvShowRepository
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

    private val _tvShow = MediatorLiveData<DetailPageState>().apply {
        value = DetailPageState(
            loading = true,
            error = "",
            tvShow = null
        )
    }
    val tvShow: LiveData<DetailPageState> get() = _tvShow

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

    //responseLiveData.observeForever{} ile de dinleyebiliriz sorun yok fakat burda bunu işlerimiz bittiğinde remove etmemiz gerekecektir.Eğer etmezseniz viewmodel destroy olsa bile temzilenmeyecektir.Çünkü bellekte viewmodelin lifecyclendan bağımsız yer tutuyor
    //mediator öyle değil lifecycle bağlı olarak otomatik temizleme yapar.
    private fun handleTvShowResponse(responseLiveData: LiveData<Response<TvShowDetail>>) {
        _tvShow.addSource(responseLiveData) { response ->
            _tvShow.postValue(
                when (response) {
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
                                error = "No TV Show data found",
                                tvShow = null
                            )
                        }
                    }

                    null -> _tvShow.value?.copy(
                        loading = false,
                        error = "Data Is Not Found!",
                        tvShow = null
                    )
                }
            )
        }
    }
}