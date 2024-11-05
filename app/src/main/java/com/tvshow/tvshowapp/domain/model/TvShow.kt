package com.tvshow.tvshowapp.domain.model

import com.google.gson.annotations.SerializedName
import com.tvshow.tvshowapp.data.mapper.TvShowMainPage

data class TvShow(
    val country: String?,
    @SerializedName("end_date")
    val endDate: Any?,
    val id: Int?,
    @SerializedName("image_thumbnail_path")
    val imageThumbnailPath: String?,
    val name: String?,
    val network: String?,
    val permalink: String?,
    @SerializedName("start_date")
    val startDate: String?,
    val status: String?,
)