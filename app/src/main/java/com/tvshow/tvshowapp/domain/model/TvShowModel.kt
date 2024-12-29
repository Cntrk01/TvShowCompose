package com.tvshow.tvshowapp.domain.model

import com.google.gson.annotations.SerializedName
import com.tvshow.tvshowapp.domain.model.response.TvShowHomeResponse

data class TvShowModel(
    val page: Int,
    val pages: Int,
    val total: String,
    @SerializedName("tv_shows")
    val tvShowHomeRespons: List<TvShowHomeResponse>
)