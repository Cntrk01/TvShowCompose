package com.tvshow.tvshowapp.domain.model.response

import com.google.gson.annotations.SerializedName

data class TvShowHomeResponse(
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