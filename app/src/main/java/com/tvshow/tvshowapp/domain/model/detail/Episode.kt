package com.tvshow.tvshowapp.domain.model.detail

import com.google.gson.annotations.SerializedName

data class Episode(
    @SerializedName("air_date")
    val airDate: String,
    val episode: Int,
    val name: String,
    val season: Int
)