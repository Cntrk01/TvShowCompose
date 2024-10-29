package com.tvshow.tvshowapp.domain.model

import com.google.gson.annotations.SerializedName

data class TvShowModel(
    val page: Int,
    val pages: Int,
    val total: String,
    @SerializedName("tv_shows")
    val tvShows: List<TvShow>
)