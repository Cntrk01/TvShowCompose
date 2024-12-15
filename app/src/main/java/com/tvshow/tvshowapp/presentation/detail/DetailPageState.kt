package com.tvshow.tvshowapp.presentation.detail

import com.tvshow.tvshowapp.domain.model.detail.TvShow

data class DetailPageState(
    val loading : Boolean = true,
    val error : String = "",
    val tvShow : TvShow? = null,
    val isShowAction : Boolean = false,
)
