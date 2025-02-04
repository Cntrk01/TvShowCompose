package com.tvshow.tvshowapp.presentation.detail

import com.tvshow.tvshowapp.domain.model.attr.TvShowDetailAttr

data class DetailPageState(
    val loading : Boolean = true,
    val error : String = "",
    val tvShow : TvShowDetailAttr? = null,
    val isShowAction : Boolean = false,
    val isSaved : Boolean = false,
)